package com.eremeeva.data.storage.roomstorage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eremeeva.data.storage.models.TemperatureEntity

@Dao
interface TemperatureEntityDao {
    @Query("select id, creationDate, value from temperature " +
            " order by creationDate desc")
    fun getAll(): List<TemperatureEntity>

    @Insert
    suspend fun insert(vararg items: TemperatureEntity)

    @Delete
    suspend fun delete(item: TemperatureEntity)
}