package com.eremeeva.goodhealth.ui.mno

import com.eremeeva.domain.models.MNOData
import com.eremeeva.goodhealth.ui.filterDialog.FilterData

sealed class MNOIntent {
    data object Get : MNOIntent()
    data class Add(val item : MNOData) : MNOIntent()
    data class Delete(val item : MNOData) : MNOIntent()
    data class SetFilterData(val data: FilterData) : MNOIntent()
    data class ShowFilterDialog(val value: Boolean) : MNOIntent()
    data class ShowMNOItem(val value: Boolean) : MNOIntent()
}