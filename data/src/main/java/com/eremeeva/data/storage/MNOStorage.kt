package com.eremeeva.data.storage

import com.eremeeva.data.storage.models.MNOEntity
import kotlinx.coroutines.flow.Flow

interface MNOStorage {
    fun getAll(): Flow<List<MNOEntity>>
    fun getByDate(date1: String?, date2: String?): Flow<List<MNOEntity>>
    suspend fun add(item: MNOEntity): Boolean
    suspend fun delete(item: MNOEntity): Boolean
}