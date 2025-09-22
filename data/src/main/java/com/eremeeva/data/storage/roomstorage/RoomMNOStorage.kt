package com.eremeeva.data.storage.roomstorage

import android.content.Context
import androidx.sqlite.SQLiteException
import com.eremeeva.data.storage.MNOStorage
import com.eremeeva.data.storage.models.MNOEntity

class RoomMNOStorage(context: Context): MNOStorage {
    private val db = GoodHealthDatabase.getInstance(context)
    private val mnoDao = db.mnoDao

    override suspend fun getAll(): List<MNOEntity>
    {
        return try{
            mnoDao.getAll()
        } catch(_: SQLiteException){
            listOf()
        }
    }

    override suspend fun getByDate(date1: String?, date2: String?): List<MNOEntity>
    {
        return try{
            mnoDao.getByDate(date1, date2)
        } catch(_: SQLiteException){
            listOf()
        }
    }

    override suspend fun add(item: MNOEntity): Boolean {
        try{
            mnoDao.insert(item)
            return true
        }
        catch(_: SQLiteException){
            return false
        }
    }

    override suspend fun delete(item: MNOEntity): Boolean {
        try{
            mnoDao.delete(item)
            return true
        }
        catch(_: SQLiteException){
            return false
        }
    }
}