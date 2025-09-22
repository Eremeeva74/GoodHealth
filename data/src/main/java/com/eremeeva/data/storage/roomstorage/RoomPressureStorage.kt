package com.eremeeva.data.storage.roomstorage

import android.content.Context
import androidx.sqlite.SQLiteException
import com.eremeeva.data.storage.PressureStorage
import com.eremeeva.data.storage.models.PressureEntity

class RoomPressureStorage(context: Context) : PressureStorage {
    private val db = GoodHealthDatabase.getInstance(context)
    private val pressureDao = db.pressureDao

    override suspend fun getAll(): List<PressureEntity> {
        return try{
            pressureDao.getAll()
        } catch(_: SQLiteException){
            listOf()
        }
    }

    override suspend fun getByDate(date1: String?, date2: String?): List<PressureEntity> {
        return try{
            pressureDao.getByDate(date1, date2)
        } catch(_: SQLiteException){
            listOf()
        }
    }

    override suspend fun add(item: PressureEntity): Boolean {
        try{
            pressureDao.insert(item)
            return true
        }
        catch(_: SQLiteException){
            return false
        }
    }

    override suspend fun delete(item: PressureEntity): Boolean {
        try{
            pressureDao.delete(item)
            return true
        }
        catch(_: SQLiteException){
            return false
        }
    }
}