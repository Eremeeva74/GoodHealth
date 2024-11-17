package com.example.goodhealth.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.goodhealth.data.database.model.PressureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PressureEntityDao {
    @Query("SELECT id, creationDate, upValue, downValue, pulse FROM pressure " +
            " order by creationDate desc")
    fun getAll(): LiveData<List<PressureEntity>>

    @Insert
    suspend fun insert(vararg items: PressureEntity)

    @Delete
    suspend fun delete(item: PressureEntity)
}