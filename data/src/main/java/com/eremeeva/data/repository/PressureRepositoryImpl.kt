package com.eremeeva.data.repository

import androidx.sqlite.SQLiteException
import com.eremeeva.data.storage.models.PressureEntity
import com.eremeeva.data.storage.roomstorage.RoomPressureStorage
import com.eremeeva.domain.models.PressureData
import com.eremeeva.domain.repository.PressureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PressureRepositoryImpl(private val roomPressureStorage: RoomPressureStorage) : PressureRepository {

    override fun getAll(): Flow<List<PressureData>>
    {
        return roomPressureStorage.getAll().map{
            it -> it.map{ PressureData(id = it.id, creationDate = it.creationDate, upValue = it.upValue, downValue = it.downValue, pulse = it.pulse) }
        }
    }

    override fun getByDate(date1: String?, date2: String?): Flow<List<PressureData>>
    {
        return roomPressureStorage.getByDate(date1, date2).map{
            it -> it.map { PressureData(id = it.id,
                                        creationDate = it.creationDate,
                                        upValue = it.upValue,
                                        downValue = it.downValue,
                                        pulse = it.pulse)
            }
        }
    }

    override suspend fun add(item: PressureData): Boolean {
        try{
            roomPressureStorage.add(PressureEntity(id = item.id,
                creationDate = item.creationDate,
                upValue = item.upValue,
                downValue = item.downValue,
                pulse = item.pulse))
            return true
        }
        catch(_: SQLiteException){
            return false
        }
    }

    override suspend fun delete(item: PressureData): Boolean {
        try{
            roomPressureStorage.delete(PressureEntity(id = item.id,
                creationDate = item.creationDate,
                upValue = item.upValue,
                downValue = item.downValue,
                pulse = item.pulse))
            return true
        }
        catch(_: SQLiteException){
            return false
        }
    }
}