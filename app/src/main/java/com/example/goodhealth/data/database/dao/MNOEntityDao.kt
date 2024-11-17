package com.example.goodhealth.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.goodhealth.data.database.model.MNOEntity

@Dao
interface MNOEntityDao {
    @Query("SELECT id, creationDate, value1, value2, value3 FROM mno " +
            " order by creationDate desc")
    fun getAll(): LiveData<List<MNOEntity>>

    @Insert
    suspend fun insert(vararg items: MNOEntity)

    @Delete
    suspend fun delete(item: MNOEntity)
}