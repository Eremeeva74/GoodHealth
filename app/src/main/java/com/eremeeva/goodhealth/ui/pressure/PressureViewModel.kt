package com.eremeeva.goodhealth.ui.pressure

import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.icu.text.SimpleDateFormat
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eremeeva.domain.models.PressureData
import com.eremeeva.domain.usecase.GetPressureUseCase
import com.eremeeva.domain.usecase.SavePressureUseCase
import com.eremeeva.goodhealth.ui.filterDialog.FilterData
import com.eremeeva.goodhealth.ui.filterDialog.FilterPeriod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.math.min


@HiltViewModel
class PressureViewModel @Inject constructor(
    private val getPressureUseCase: GetPressureUseCase,
    private val savePressureUseCase: SavePressureUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PressureState(isLoading = true,
        pressureList = null,
        filterData = FilterData(periodType = FilterPeriod.ALL.value, startDate = 0, endDate =0),
        showFilterDialog = false,
        showPressureItemDialog = false,
        error = null))
    val state: StateFlow<PressureState> = _state.asStateFlow()

    fun handlePressureIntent(intent: PressureIntent) {
        when (intent) {
            is PressureIntent.Get -> get()
            is PressureIntent.Add -> add(intent.item)
            is PressureIntent.Delete -> delete(intent.item)
            is PressureIntent.SetFilterData -> setFilterData(intent.data)
            is PressureIntent.ShowFilterDialog -> showFilterDialog(intent.value)
            is PressureIntent.ShowPressureItem -> showPressureItem(intent.value)
        }
    }

    init {
        Log.e("AAA", "PressureViewModel created")
    }

    override fun onCleared() {
        Log.e("AAA", "PressureViewModel cleared")
        super.onCleared()
    }

    private fun add(item : PressureData)
    {
        viewModelScope.launch {
            savePressureUseCase.add(item)
            get()
        }
    }

    private fun delete(item : PressureData)
    {
        viewModelScope.launch {
            savePressureUseCase.delete(item)
            get()
        }
    }

    private fun get()
    {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                if (_state.value.filterData.periodType == FilterPeriod.ALL.value){
                    val list = getPressureUseCase.getAll()
                    _state.value = _state.value.copy(isLoading = false, pressureList = list)
                }
                else{
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    var d1: Long = _state.value.filterData.startDate ?: 0
                    var d2: Long = _state.value.filterData.endDate ?: 0
                    val calendar: Calendar = Calendar.getInstance()
                    if (d1 == (0).toLong()){
                        calendar.set(1970, 1,1,0, 0, 0)
                        calendar.timeInMillis.also { d1 = it }
                    }
                    if (d2 == (0).toLong()){
                        calendar.set(2100, 1,1,0, 0, 0)
                        calendar.timeInMillis.also { d2 = it }
                    }

                    val list = getPressureUseCase.getByDate(formatter.format(d1), formatter.format(d2))
                    _state.value = _state.value.copy(isLoading = false, pressureList = list)
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            } finally {
                _state.update { it.copy(isLoading = false, error = null) }
            }
        }
    }

    private fun setFilterData(data : FilterData)
    {
        _state.update { it.copy(filterData = data) }
        get()
    }

    private fun showFilterDialog(value : Boolean)
    {
        _state.update { it.copy(showFilterDialog = value) }
    }

    private fun showPressureItem(value : Boolean)
    {
        _state.update { it.copy(showPressureItemDialog = value) }
    }
/*
    private fun createPDF(list: List<PressureData>?) {
        if (list != null) {
            viewModelScope.launch {
                val document = PdfDocument()

                val width = 612
                val height = 792
                val pageMargin = 20

                val numberOfColumns = 3
                val numberOfRows = list.count()
                val cellWidth = (width - pageMargin * 2) / numberOfColumns
                val cellHeight = 15
                val linePaint = Paint().apply { style = Paint.Style.STROKE; strokeWidth = 1f }
                val textPaint = Paint().apply { style = Paint.Style.FILL; textSize = 12f }
                val textCaptionPaint = Paint().apply { style = Paint.Style.FILL; textSize = 12f }
                textCaptionPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

                val linePerPage = (height - pageMargin * 2) / (cellHeight) - 1
                var pageNumber = 1
                var index = 0
                while (index < numberOfRows){

                    val pageInfo = PdfDocument.PageInfo.Builder(width, height, pageNumber).create()
                    val page = document.startPage(pageInfo)
                    val canvas = page.canvas

                    // caption
                    canvas.drawLine(pageMargin.toFloat(),
                        pageMargin.toFloat(),
                        width.toFloat() - pageMargin,
                        pageMargin.toFloat(),
                        linePaint)

                    for (j in 0..< numberOfColumns) {
                        val y = pageMargin + cellHeight.toFloat() - 3f
                        when (j){
                            0 -> canvas.drawText("Дата", pageMargin + 5 + j * cellWidth.toFloat(),
                                y, textCaptionPaint)
                            1 -> canvas.drawText("Давление", pageMargin + 5 + j * cellWidth.toFloat(),
                                y, textCaptionPaint)
                            2 -> canvas.drawText("Пульс", pageMargin + 5 + j * cellWidth.toFloat(),
                                y, textCaptionPaint)
                        }
                    }

                    canvas.drawLine(pageMargin.toFloat(),
                        pageMargin.toFloat() + cellHeight,
                        width.toFloat() - pageMargin,
                        pageMargin.toFloat() + cellHeight,
                        linePaint)

                    // text and horizontal lines
                    for(i in 0..< min(linePerPage - 1, numberOfRows - index)){
                        val y = pageMargin + i * cellHeight.toFloat() - 3f + 2 * cellHeight.toFloat()
                        for (j in 0..< numberOfColumns) {
                            when (j){
                                0 -> canvas.drawText(list[index + i].creationDate, pageMargin + 5 + j * cellWidth.toFloat(),
                                    y, textPaint)
                                1 -> canvas.drawText("${list[index + i].upValue} / ${list[index + i].downValue}", pageMargin + 5 + j * cellWidth.toFloat(),
                                    y, textPaint)
                                2 -> canvas.drawText(list[index + i].pulse.toString(), pageMargin + 5 + j * cellWidth.toFloat(),
                                    y, textPaint)
                            }
                        }

                        canvas.drawLine(
                            pageMargin.toFloat() ,
                            pageMargin.toFloat() + i * cellHeight.toFloat() + 2 * cellHeight.toFloat(),
                            width.toFloat() - pageMargin,
                            pageMargin.toFloat() + i * cellHeight.toFloat() + 2 * cellHeight.toFloat(),
                            linePaint)
                    }

                    // vertical lines
                    for (j in 0..numberOfColumns){
                        canvas.drawLine(pageMargin.toFloat() + j * cellWidth.toFloat(),
                            pageMargin.toFloat(),
                            pageMargin.toFloat() + j * cellWidth.toFloat(),
                            min(linePerPage, numberOfRows - index + 1) * cellHeight + pageMargin.toFloat(),
                            linePaint)
                    }

                    document.finishPage(page)
                    pageNumber++
                    index += min(linePerPage - 1, numberOfRows - index)
                }

                val filename = "pressure.pdf"
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename)

                document.writeTo(FileOutputStream(file))
                document.close()
            }
        }
    }
*/
}


