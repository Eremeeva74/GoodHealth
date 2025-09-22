package com.eremeeva.data.storage

import com.eremeeva.data.storage.models.MNOEntity

interface MNOStorage {
    suspend fun getAll(): List<MNOEntity>
    suspend fun getByDate(date1: String?, date2: String?): List<MNOEntity>
    suspend fun add(item: MNOEntity): Boolean
    suspend fun delete(item: MNOEntity): Boolean
}