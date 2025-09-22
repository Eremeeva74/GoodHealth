package com.eremeeva.goodhealth.ui.mno

import com.eremeeva.domain.models.MNOData
import com.eremeeva.goodhealth.ui.filterDialog.FilterData

data class MNOState(val isLoading: Boolean,
                    val mnoList: List<MNOData>?,
                    val filterData: FilterData,
                    val showFilterDialog: Boolean,
                    val showMNOItemDialog: Boolean,
                    val error: String?)
