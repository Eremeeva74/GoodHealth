package com.eremeeva.goodhealth.ui.pressure

import com.eremeeva.domain.models.PressureData
import com.eremeeva.goodhealth.ui.filterDialog.FilterData

sealed class PressureIntent {
    object Get : PressureIntent()
    data class Add(val item : PressureData) : PressureIntent()
    data class Delete(val item : PressureData) : PressureIntent()
    data class SetFilterData(val data: FilterData) : PressureIntent()
    data class ShowFilterDialog(val value: Boolean) : PressureIntent()
    data class ShowPressureItem(val value: Boolean) : PressureIntent()
}