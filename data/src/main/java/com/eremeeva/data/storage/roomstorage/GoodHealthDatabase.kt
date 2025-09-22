package com.eremeeva.data.storage.roomstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eremeeva.data.storage.roomstorage.dao.MNOEntityDao
import com.eremeeva.data.storage.roomstorage.dao.PressureEntityDao
import com.eremeeva.data.storage.roomstorage.dao.TemperatureEntityDao
import com.eremeeva.data.storage.models.MNOEntity
import com.eremeeva.data.storage.models.PressureEntity
import com.eremeeva.data.storage.models.TemperatureEntity

@Database(entities =
        [
            MNOEntity::class,
            PressureEntity::class,
            TemperatureEntity::class
        ],
    version = 1,
    exportSchema = false)

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
                    ).fallbackToDestructiveMigration(dropAllTables = false).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


