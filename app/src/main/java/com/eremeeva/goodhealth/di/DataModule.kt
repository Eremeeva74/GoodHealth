package com.eremeeva.goodhealth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import com.eremeeva.data.repository.MNORepositoryImpl
import com.eremeeva.data.repository.PressureRepositoryImpl
import com.eremeeva.data.storage.roomstorage.RoomMNOStorage
import com.eremeeva.data.storage.roomstorage.RoomPressureStorage
import com.eremeeva.domain.repository.MNORepository
import com.eremeeva.domain.repository.PressureRepository


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRoomMNOStorage(@ApplicationContext context: Context): RoomMNOStorage {
        return RoomMNOStorage(context)
    }

    @Provides
    @Singleton
    fun provideMNORepository(roomMNOStorage: RoomMNOStorage): MNORepository{
        return MNORepositoryImpl(roomMNOStorage)
    }

    @Provides
    @Singleton
    fun provideRoomPressureStorage(@ApplicationContext context: Context): RoomPressureStorage{
        return RoomPressureStorage(context)
    }

    @Provides
    @Singleton
    fun providePressureRepository(roomPressureStorage: RoomPressureStorage): PressureRepository{
        return PressureRepositoryImpl(roomPressureStorage)
    }
}