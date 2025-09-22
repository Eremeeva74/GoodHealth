package com.eremeeva.domain.models

data class PressureData(
    val id: Int?,
    val creationDate : String,
    val upValue : Int,
    val downValue : Int,
    val pulse : Int)