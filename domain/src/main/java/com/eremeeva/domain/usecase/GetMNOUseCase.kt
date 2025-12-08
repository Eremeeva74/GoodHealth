package com.eremeeva.domain.usecase

import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.repository.MNORepository
import kotlinx.coroutines.flow.Flow

class GetMNOUseCase(private val mnoRepository: MNORepository) {

    fun getAll() : Flow<List<MNOData>> {
        return mnoRepository.getAll()
    }

    fun getByDate(date1: String?, date2: String?): Flow<List<MNOData>>{
        return mnoRepository.getByDate(date1, date2)
    }
}