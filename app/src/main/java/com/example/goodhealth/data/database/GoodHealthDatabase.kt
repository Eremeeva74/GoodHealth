package com.example.goodhealth.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.goodhealth.data.database.dao.MNOEntityDao
import com.example.goodhealth.data.database.dao.PressureEntityDao
import com.example.goodhealth.data.database.dao.TemperatureEntityDao
import com.example.goodhealth.data.database.model.MNOEntity
import com.example.goodhealth.data.database.model.PressureEntity
import com.example.goodhealth.data.database.model.TemperatureEntity

@Database(entities =
        [
            MNOEntity::class,
            PressureEntity::class,
            TemperatureEntity::class
        ],
    version = 3)

abstract class GoodHealthDatabase : RoomDatabase() {
    abstract val mnoDao : MNOEntityDao
    abstract val pressureDao : PressureEntityDao
    abstract val temperatureDao : TemperatureEntityDao

    companion object {
        private var INSTANCE: GoodHealthDatabase? = null

        fun getInstance(context: Context): GoodHealthDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        klass = GoodHealthDatabase::class.java,
                        name = "goodhealth.db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


