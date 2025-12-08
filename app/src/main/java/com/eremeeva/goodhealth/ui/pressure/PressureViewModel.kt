package com.eremeeva.goodhealth.ui.pressure

import android.icu.text.SimpleDateFormat
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
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class PressureViewModel @Inject constructor(
    private val getPressureUseCase: GetPressureUseCase,
    private val savePressureUseCase: SavePressureUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PressureState(
        pressureList = emptyFlow(),
        filterData = FilterData(periodType = FilterPeriod.ALL.value, startDate = null, endDate = null),
        showFilterDialog = false,
        showPressureItemDialog = false,
        error = null,
        loading = true))
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
        try {
            _state.value = _state.value.copy(loading = true, pressureList = getPressureUseCase.getAll())
        }
        finally {
            _state.update { it.copy(loading = false, error = null) }
        }
    }

    private fun add(item : PressureData)
    {
        viewModelScope.launch {
            savePressureUseCase.add(item)
        }
    }

    private fun delete(item : PressureData)
    {
        viewModelScope.launch {
            savePressureUseCase.delete(item)
        }
    }

    private fun get()
    {
        try {
            _state.value = _state.value.copy(loading = true)
            //delay(3000)
            if (_state.value.filterData.periodType == FilterPeriod.ALL.value){
                _state.value = _state.value.copy(pressureList = getPressureUseCase.getAll())
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

                _state.value = _state.value.copy(pressureList = getPressureUseCase.getByDate(formatter.format(d1), formatter.format(d2)))
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e.message) }
        } finally {
            _state.update { it.copy(loading = false, error = null) }
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
}


