package com.eremeeva.domain.repository

import com.eremeeva.domain.models.PressureData

interface PressureRepository {
    suspend fun getAll(): List<PressureData>
    suspend fun getByDate(date1: String?, date2: String?): List<PressureData>
    suspend fun add(item: PressureData): Boolean
    suspend fun delete(item: PressureData): Boolean
}