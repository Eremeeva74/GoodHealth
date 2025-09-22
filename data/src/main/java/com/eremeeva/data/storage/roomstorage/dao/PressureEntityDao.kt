package com.eremeeva.data.storage.roomstorage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eremeeva.data.storage.models.PressureEntity

@Dao
interface PressureEntityDao {
    @Query("select id, creationDate, upValue, downValue, pulse from pressure " +
            " order by creationDate desc")
    suspend fun getAll(): List<PressureEntity>

    @Query( "   select id, creationDate, upValue, downValue, pulse from pressure " +
            "   where creationDate between :date1 and :date2" +
            "   order by creationDate desc"
    )
    suspend fun getByDate(date1: String?, date2: String?): List<PressureEntity>

    @Insert
    suspend fun insert(item: PressureEntity)

    @Delete
    suspend fun delete(item: PressureEntity)
}