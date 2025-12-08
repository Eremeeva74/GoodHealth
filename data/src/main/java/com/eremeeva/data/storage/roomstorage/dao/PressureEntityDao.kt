package com.eremeeva.data.storage.roomstorage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eremeeva.data.storage.models.PressureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PressureEntityDao {
    @Query("select id, creationDate, upValue, downValue, pulse from pressure " +
            " order by creationDate desc")
    fun getAll(): Flow<List<PressureEntity>>

    @Query( "   select id, creationDate, upValue, downValue, pulse from pressure " +
            "   where creationDate between :date1 and :date2" +
            "   order by creationDate desc"
    )
    fun getByDate(date1: String?, date2: String?): Flow<List<PressureEntity>>

    @Insert
    suspend fun insert(item: PressureEntity)

    @Delete
    suspend fun delete(item: PressureEntity)
}