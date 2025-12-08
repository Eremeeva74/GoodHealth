package com.eremeeva.domain.repository

import com.eremeeva.domain.models.PressureData
import kotlinx.coroutines.flow.Flow

interface PressureRepository {
    fun getAll(): Flow<List<PressureData>>
    fun getByDate(date1: String?, date2: String?): Flow<List<PressureData>>
    suspend fun add(item: PressureData): Boolean
    suspend fun delete(item: PressureData): Boolean
}