package com.eremeeva.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mno")
data class MNOEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "creationDate") val creationDate : String,
    @ColumnInfo(name = "value1") val value1 : Double,
    @ColumnInfo(name = "value2") val value2 : Double,
    @ColumnInfo(name = "value3") val value3 : Double
)



