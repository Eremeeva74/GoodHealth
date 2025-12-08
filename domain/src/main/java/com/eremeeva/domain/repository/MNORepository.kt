package com.eremeeva.domain.repository

import com.eremeeva.domain.models.MNOData
import kotlinx.coroutines.flow.Flow

interface MNORepository {
    fun getAll(): Flow<List<MNOData>>
    fun getByDate(date1: String?, date2: String?): Flow<List<MNOData>>
    suspend fun add(item: MNOData): Boolean
    suspend fun delete(item: MNOData): Boolean
}