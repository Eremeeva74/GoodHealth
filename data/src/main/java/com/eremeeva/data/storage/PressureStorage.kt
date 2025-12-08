package com.eremeeva.data.storage

import com.eremeeva.data.storage.models.PressureEntity
import kotlinx.coroutines.flow.Flow

interface PressureStorage {
    fun getAll(): Flow<List<PressureEntity>>
    fun getByDate(date1: String?, date2: String?): Flow<List<PressureEntity>>
    suspend fun add(item: PressureEntity): Boolean
    suspend fun delete(item: PressureEntity): Boolean
}