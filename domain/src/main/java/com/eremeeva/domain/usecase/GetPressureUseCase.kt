package com.eremeeva.domain.usecase

import com.eremeeva.domain.models.PressureData
import com.eremeeva.domain.repository.PressureRepository

class GetPressureUseCase(private val pressureRepository: PressureRepository) {

    suspend fun getAll() : List<PressureData> {
        return pressureRepository.getAll()
    }

    suspend fun getByDate(date1: String?, date2: String?): List<PressureData>{
        return pressureRepository.getByDate(date1, date2)
    }
}