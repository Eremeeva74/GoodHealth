package com.eremeeva.domain.usecase

import com.eremeeva.domain.models.PressureData
import com.eremeeva.domain.repository.PressureRepository

class SavePressureUseCase(private val pressureRepository: PressureRepository) {
    suspend fun add(item: PressureData): Boolean {
        return pressureRepository.add(item)
    }

    suspend fun delete(item: PressureData): Boolean {
        return pressureRepository.delete(item)
    }
}