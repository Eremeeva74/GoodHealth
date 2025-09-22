package com.eremeeva.goodhealth.ui.pressure

import com.eremeeva.domain.models.PressureData
import com.eremeeva.goodhealth.ui.filterDialog.FilterData

data class PressureState(val isLoading: Boolean,
                         val pressureList: List<PressureData>?,
                         val filterData: FilterData,
                         val showFilterDialog: Boolean,
                         val showPressureItemDialog: Boolean,
                         val error: String?)
