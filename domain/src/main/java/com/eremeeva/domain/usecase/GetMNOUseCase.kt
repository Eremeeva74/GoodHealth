package com.eremeeva.domain.usecase

import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.repository.MNORepository

class GetMNOUseCase(private val mnoRepository: MNORepository) {

    suspend fun getAll() : List<MNOData> {
        return mnoRepository.getAll()
    }

    suspend fun getByDate(date1: String?, date2: String?): List<MNOData>{
        return mnoRepository.getByDate(date1, date2)
    }
}