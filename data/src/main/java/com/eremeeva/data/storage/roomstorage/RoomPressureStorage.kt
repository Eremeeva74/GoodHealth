package com.eremeeva.data.storage.roomstorage

import android.content.Context
import android.util.Log
import androidx.sqlite.SQLiteException
import com.eremeeva.data.storage.PressureStorage
import com.eremeeva.data.storage.models.PressureEntity
import kotlinx.coroutines.flow.Flow

class RoomPressureStorage(context: Context) : PressureStorage {
    private val db = GoodHealthDatabase.getInstance(context)
    private val pressureDao = db.pressureDao

    override fun getAll(): Flow<List<PressureEntity>> {
        return pressureDao.getAll()
    }

    override fun getByDate(date1: String?, date2: String?): Flow<List<PressureEntity>> {
        return pressureDao.getByDate(date1, date2)
    }

    override suspend fun add(item: PressureEntity): Boolean {
        try{
            pressureDao.insert(item)
            return true
        }
        catch(e: SQLiteException){
            Log.e("GoodHealth", "Ошибка при вставке элемента PressureEntity", e)
            return false
        }
    }

    override suspend fun delete(item: PressureEntity): Boolean {
        try{
            pressureDao.delete(item)
            return true
        }
        catch(e: SQLiteException){
            Log.e("GoodHealth", "Ошибка при удалении элемента PressureEntity", e)
            return false
        }
    }
}