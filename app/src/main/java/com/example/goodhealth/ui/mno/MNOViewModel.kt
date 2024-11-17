package com.example.goodhealth.ui.mno

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goodhealth.data.MNORepository
import com.example.goodhealth.data.database.GoodHealthDatabase
import com.example.goodhealth.data.database.model.MNOEntity

class MNOViewModel(
    private val application : Application,
    appContext: Context
) : ViewModel() {

    val mnoList : LiveData<List<MNOEntity>>
    private val mnoRepository: MNORepository

    init {
        val db = GoodHealthDatabase.getInstance(appContext)
        val mnoDao = db.mnoDao
        mnoRepository = MNORepository(mnoDao)
        mnoList = mnoRepository.mnoList
    }

    fun add(item : MNOEntity)
    {
        mnoRepository.add(item)
    }

    fun delete(item : MNOEntity)
    {
        mnoRepository.delete(item)
    }

    companion object {
        fun provideFactory(
            application : Application,
            appContext: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MNOViewModel(application, appContext) as T
            }
        }
    }
}
