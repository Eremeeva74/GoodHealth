package com.eremeeva.goodhealth.ui.pressure

import com.eremeeva.domain.models.PressureData
import com.eremeeva.goodhealth.ui.filterDialog.FilterData
import kotlinx.coroutines.flow.Flow

data class PressureState(
    val pressureList: Flow<List<PressureData>>,
    val filterData: FilterData,
    val showFilterDialog: Boolean,
    val showPressureItemDialog: Boolean,
    val error: String?,
    val loading: Boolean)
