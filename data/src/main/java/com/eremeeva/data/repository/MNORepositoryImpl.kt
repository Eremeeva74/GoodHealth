package com.eremeeva.data.repository

import androidx.sqlite.SQLiteException
import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.repository.MNORepository
import com.eremeeva.data.storage.models.MNOEntity
import com.eremeeva.data.storage.roomstorage.RoomMNOStorage

class MNORepositoryImpl(private val roomMNOStorage: RoomMNOStorage): MNORepository {

    override suspend fun getAll(): List<MNOData>
    {
        val list: List<MNOEntity> = roomMNOStorage.getAll()
        return list.map{ MNOData(it.id, it.creationDate, it.value1, it.value2, it.value3) }
    }

    override suspend fun getByDate(date1: String?, date2: String?): List<MNOData>
    {
        val list: List<MNOEntity> = roomMNOStorage.getByDate(date1, date2)
        return list.map{ MNOData(it.id, it.creationDate, it.value1, it.value2, it.value3) }
    }

    override suspend fun add(item: MNOData): Boolean {
        return roomMNOStorage.add(MNOEntity(item.id, item.creationDate, item.value1, item.value2, item.value3))
    }

    override suspend fun delete(item: MNOData): Boolean {
        return roomMNOStorage.delete(MNOEntity(item.id, item.creationDate, item.value1, item.value2, item.value3))
    }
}