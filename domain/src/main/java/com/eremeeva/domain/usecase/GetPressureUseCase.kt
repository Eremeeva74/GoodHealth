package com.eremeeva.domain.usecase

import com.eremeeva.domain.models.PressureData
import com.eremeeva.domain.repository.PressureRepository
import kotlinx.coroutines.flow.Flow

class GetPressureUseCase(private val pressureRepository: PressureRepository) {

    fun getAll() : Flow<List<PressureData>> {
        return pressureRepository.getAll()
    }

    fun getByDate(date1: String?, date2: String?): Flow<List<PressureData>>{
        return pressureRepository.getByDate(date1, date2)
    }
}