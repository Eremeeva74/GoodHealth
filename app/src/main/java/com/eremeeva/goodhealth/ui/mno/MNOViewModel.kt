package com.eremeeva.goodhealth.ui.mno

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.usecase.GetMNOUseCase
import com.eremeeva.domain.usecase.SaveMNOUseCase
import com.eremeeva.goodhealth.ui.filterDialog.FilterData
import com.eremeeva.goodhealth.ui.filterDialog.FilterPeriod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class  MNOViewModel @Inject constructor(
    private val getMNOUseCase: GetMNOUseCase,
    private val saveMNOUseCase: SaveMNOUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        MNOState(isLoading = true,
            mnoList = null,
            filterData = FilterData(periodType = FilterPeriod.ALL.value, startDate = 0, endDate = 0),
            showFilterDialog = false,
            showMNOItemDialog = false,
            error = null)
    )
    val state: StateFlow<MNOState> = _state.asStateFlow()

    fun handleMNOIntent(intent: MNOIntent) {
        when (intent) {
            is MNOIntent.Get -> get()
            is MNOIntent.Add -> add(intent.item)
            is MNOIntent.Delete -> delete(intent.item)
            is MNOIntent.SetFilterData -> setFilterData(intent.data)
            is MNOIntent.ShowFilterDialog -> showFilterDialog(intent.value)
            is MNOIntent.ShowMNOItem -> showMNOItem(intent.value)
        }
    }

    init {
        Log.e("AAA", "MNOViewModel created")
    }

    override fun onCleared() {
        Log.e("AAA", "MNOViewModel cleared")
        super.onCleared()
    }

    private fun add(item : MNOData)
    {
        viewModelScope.launch {
            saveMNOUseCase.add(item)
            //val formatter = SimpleDateFormat("yyyy-MM-dd   HH:mm", Locale.getDefault())
            //saveMNOUseCase.add(MNOData(id = 1, creationDate = formatter.format(Date()), value1 = 1.0, value2 = 2.0, value3 = 3.0))
            get()
        }
    }

    private fun delete(item : MNOData)
    {
        viewModelScope.launch {
            saveMNOUseCase.delete(item)
            get()
        }
    }

    private fun get()
    {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                if (_state.value.filterData.periodType == FilterPeriod.ALL.value){
                    val list = getMNOUseCase.getAll()
                    _state.value = _state.value.copy(isLoading = false, mnoList = list)
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

                    val list = getMNOUseCase.getByDate(formatter.format(d1), formatter.format(d2))
                    _state.value = _state.value.copy(isLoading = false, mnoList = list)
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

    private fun showMNOItem(value : Boolean)
    {
        _state.update { it.copy(showMNOItemDialog = value) }
    }
}

