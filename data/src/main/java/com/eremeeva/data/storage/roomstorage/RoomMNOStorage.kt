package com.eremeeva.data.storage.roomstorage

import android.content.Context
import android.util.Log
import androidx.sqlite.SQLiteException
import com.eremeeva.data.storage.MNOStorage
import com.eremeeva.data.storage.models.MNOEntity
import kotlinx.coroutines.flow.Flow

class RoomMNOStorage(context: Context): MNOStorage {
    private val db = GoodHealthDatabase.getInstance(context)
    private val mnoDao = db.mnoDao

    override fun getAll(): Flow<List<MNOEntity>>
    {
        return mnoDao.getAll()
    }

    override fun getByDate(date1: String?, date2: String?): Flow<List<MNOEntity>>
    {
        return mnoDao.getByDate(date1, date2)
    }

    override suspend fun add(item: MNOEntity): Boolean {
        try{
            mnoDao.insert(item)
            return true
        }
        catch(e: SQLiteException){
            Log.e("GoodHealth", "Ошибка при вставке элемента MNOEntity", e)
            return false
        }
    }

    override suspend fun delete(item: MNOEntity): Boolean {
        try{
            mnoDao.delete(item)
            return true
        }
        catch(e: SQLiteException){
            Log.e("GoodHealth", "Ошибка при удалении элемента MNOEntity", e)
            return false
        }
    }
}