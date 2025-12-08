package com.eremeeva.goodhealth.ui.mno

import com.eremeeva.domain.models.MNOData
import com.eremeeva.goodhealth.ui.filterDialog.FilterData
import kotlinx.coroutines.flow.Flow

data class MNOState(
    val mnoList: Flow<List<MNOData>>,
    val filterData: FilterData,
    val showFilterDialog: Boolean,
    val showMNOItemDialog: Boolean,
    val error: String?,
    val loading: Boolean
)
