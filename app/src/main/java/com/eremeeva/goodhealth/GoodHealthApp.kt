package com.eremeeva.goodhealth

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

object GoodHealthScreens {
    const val START_ROUTE = "start"
    const val MNO_ROUTE = "mno"
    const val MNO_ITEM_ROUTE = "mnoItem"
    const val PRESSURE_ROUTE = "pressure"
    const val PRESSURE_ITEM_ROUTE = "pressureItem"
    const val TEMPERATURE_ROUTE = "temperature"
    const val TEMPERATURE_ITEM_ROUTE = "temperatureItem"
}

@HiltAndroidApp
class GoodHealthApplication : Application()


