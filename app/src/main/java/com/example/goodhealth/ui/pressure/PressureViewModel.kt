package com.example.goodhealth.ui.pressure

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goodhealth.data.PressureRepository
import com.example.goodhealth.data.database.GoodHealthDatabase
import com.example.goodhealth.data.database.model.PressureEntity

class PressureViewModel(
    appContext: Context
) : ViewModel() {

    val pressureList : LiveData<List<PressureEntity>>
    private val pressureRepository: PressureRepository

    init {
        val db = GoodHealthDatabase.getInstance(appContext)
        val pressureDao = db.pressureDao
        pressureRepository = PressureRepository(pressureDao)
        pressureList = pressureRepository.pressureList
    }

    fun add(item : PressureEntity)
    {
        pressureRepository.add(item)
    }

    fun delete(item : PressureEntity)
    {
        pressureRepository.delete(item)
    }

    companion object {
        fun provideFactory(
            appContext: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PressureViewModel(appContext) as T
            }
        }
    }
}


