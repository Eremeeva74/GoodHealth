package com.example.goodhealth.data

import androidx.lifecycle.LiveData
import com.example.goodhealth.data.database.dao.MNOEntityDao
import com.example.goodhealth.data.database.dao.PressureEntityDao
import com.example.goodhealth.data.database.model.MNOEntity
import com.example.goodhealth.data.database.model.PressureEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MNORepository(private val mnoDao: MNOEntityDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val mnoList: LiveData<List<MNOEntity>> = mnoDao.getAll()

    fun add(item: MNOEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            mnoDao.insert(item)
        }
    }

    fun delete(item: MNOEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            mnoDao.delete(item)
        }
    }
}


class PressureRepository(private val pressureDao: PressureEntityDao)  {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val pressureList: LiveData<List<PressureEntity>> = pressureDao.getAll()

    fun add(item: PressureEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            pressureDao.insert(item)
        }
    }

    fun delete(item: PressureEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            pressureDao.delete(item)
        }
    }
}




