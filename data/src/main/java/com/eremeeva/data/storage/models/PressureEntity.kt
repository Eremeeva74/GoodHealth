package com.eremeeva.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pressure")
data class PressureEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "creationDate") val creationDate : String,
    @ColumnInfo(name = "upValue") val upValue : Int,
    @ColumnInfo(name = "downValue") val downValue : Int,
    @ColumnInfo(name = "pulse") val pulse : Int
)
