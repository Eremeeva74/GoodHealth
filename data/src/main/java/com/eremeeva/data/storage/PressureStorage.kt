package com.eremeeva.data.storage

import com.eremeeva.data.storage.models.PressureEntity

interface PressureStorage {
    suspend fun getAll(): List<PressureEntity>
    suspend fun getByDate(date1: String?, date2: String?): List<PressureEntity>
    suspend fun add(item: PressureEntity): Boolean
    suspend fun delete(item: PressureEntity): Boolean
}