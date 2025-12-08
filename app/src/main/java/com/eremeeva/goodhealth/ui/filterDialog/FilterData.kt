package com.eremeeva.goodhealth.ui.filterDialog

data class FilterData(
    val periodType: String = "",
    val startDate: Long? = null,
    val endDate: Long? = null
)

enum class FilterPeriod(val value: String){
    ALL("все"), WEEK("за неделю"), MONTH("за месяц"), PERIOD("за период")
}


