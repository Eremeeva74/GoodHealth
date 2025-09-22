package com.eremeeva.goodhealth.di

import com.eremeeva.domain.repository.MNORepository
import com.eremeeva.domain.repository.PressureRepository
import com.eremeeva.domain.usecase.GetMNOUseCase
import com.eremeeva.domain.usecase.GetPressureUseCase
import com.eremeeva.domain.usecase.SaveMNOUseCase
import com.eremeeva.domain.usecase.SavePressureUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetMNOUseCase(mnoRepository: MNORepository): GetMNOUseCase{
        return GetMNOUseCase(mnoRepository)
    }

    @Provides
    fun provideSaveMNOUseCase(mnoRepository: MNORepository): SaveMNOUseCase{
        return SaveMNOUseCase(mnoRepository)
    }

    @Provides
    fun provideGetPressureUseCase(pressureRepository: PressureRepository): GetPressureUseCase{
        return GetPressureUseCase(pressureRepository)
    }

    @Provides
    fun provideSavePressureUseCase(pressureRepository: PressureRepository): SavePressureUseCase{
        return SavePressureUseCase(pressureRepository)
    }
}