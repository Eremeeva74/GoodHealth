package com.example.goodhealth.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.goodhealth.data.database.model.TemperatureEntity

@Dao
interface TemperatureEntityDao {
    @Query("select id, creationDate, value from temperature " +
            " order by creationDate desc")
    fun getAll(): LiveData<List<TemperatureEntity>>

    @Insert
    suspend fun insert(vararg items: TemperatureEntity)

    @Delete
    suspend fun delete(item: TemperatureEntity)
}