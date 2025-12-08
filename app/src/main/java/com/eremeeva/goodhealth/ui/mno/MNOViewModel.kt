package com.eremeeva.goodhealth.ui.mno

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.usecase.GetMNOUseCase
import com.eremeeva.domain.usecase.SaveMNOUseCase
import com.eremeeva.goodhealth.BuildConfig
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
class  MNOViewModel @Inject constructor(
    private val getMNOUseCase: GetMNOUseCase,
    private val saveMNOUseCase: SaveMNOUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        MNOState(
            mnoList = emptyFlow(),
            filterData = FilterData(periodType = FilterPeriod.ALL.value, startDate = null, endDate = null),
            showFilterDialog = false,
            showMNOItemDialog = false,
            error = null,
            loading = true)
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
        try{
            _state.value = _state.value.copy(loading = true, mnoList = getMNOUseCase.getAll())
        }
        finally {
            _state.update { it.copy(loading = false, error = null) }
        }
    }

    private fun add(item : MNOData)
    {
        viewModelScope.launch {
            saveMNOUseCase.add(item)
        }
    }

    private fun delete(item : MNOData)
    {
        viewModelScope.launch {
            saveMNOUseCase.delete(item)
        }
    }

    private fun get()
    {
        try {
            _state.value = _state.value.copy(loading = true)
            if (_state.value.filterData.periodType == FilterPeriod.ALL.value){
                _state.value = _state.value.copy(mnoList = getMNOUseCase.getAll())
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

                _state.value = _state.value.copy(mnoList = getMNOUseCase.getByDate(formatter.format(d1), formatter.format(d2)))
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e.message) }
        } finally {
            _state.update { it.copy(error = null, loading = false) }
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

