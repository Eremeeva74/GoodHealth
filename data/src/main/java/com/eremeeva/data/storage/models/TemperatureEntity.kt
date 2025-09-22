package com.eremeeva.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "temperature")
data class TemperatureEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "creationDate") val creationDate : String,
    @ColumnInfo(name = "value") val value : Double
)
