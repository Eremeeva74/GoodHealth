package com.eremeeva.data.storage.roomstorage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eremeeva.data.storage.models.MNOEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MNOEntityDao {
    @Query("select id, creationDate, value1, value2, value3 from mno " +
                " order by id desc "
    )
    fun getAll(): Flow<List<MNOEntity>>

    @Query( "select id, creationDate, value1, value2, value3 from mno " +
            "   where creationDate between :date1 and :date2" +
            "   order by id desc"
    )
    fun getByDate(date1: String?, date2: String?): Flow<List<MNOEntity>>

    @Insert
    suspend fun insert(item: MNOEntity)

    @Delete
    suspend fun delete(item: MNOEntity)
}