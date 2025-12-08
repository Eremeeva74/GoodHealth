package com.eremeeva.data.repository

import com.eremeeva.data.storage.models.MNOEntity
import com.eremeeva.data.storage.roomstorage.RoomMNOStorage
import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.repository.MNORepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class MNORepositoryImpl(private val roomMNOStorage: RoomMNOStorage): MNORepository {

    override fun getAll(): Flow<List<MNOData>> {
        return roomMNOStorage.getAll().map{
                it -> it.map{ MNOData(it.id, it.creationDate, it.value1, it.value2, it.value3) }
        }
    }

    override fun getByDate(date1: String?, date2: String?): Flow<List<MNOData>>{
        return roomMNOStorage.getByDate(date1, date2).map{
            it -> it.map{MNOData(it.id, it.creationDate, it.value1, it.value2, it.value3) }
        }
    }

    override suspend fun add(item: MNOData): Boolean {
        return roomMNOStorage.add(MNOEntity(item.id, item.creationDate, item.value1, item.value2, item.value3))
    }

    override suspend fun delete(item: MNOData): Boolean {
        return roomMNOStorage.delete(MNOEntity(item.id, item.creationDate, item.value1, item.value2, item.value3))
    }
}


