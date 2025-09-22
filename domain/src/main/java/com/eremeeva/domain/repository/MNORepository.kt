package com.eremeeva.domain.repository

import com.eremeeva.domain.models.MNOData

interface MNORepository {
    suspend fun getAll(): List<MNOData>
    suspend fun getByDate(date1: String?, date2: String?): List<MNOData>
    suspend fun add(item: MNOData): Boolean
    suspend fun delete(item: MNOData): Boolean
}