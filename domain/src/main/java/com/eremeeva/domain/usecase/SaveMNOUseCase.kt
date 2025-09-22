package com.eremeeva.domain.usecase

import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.repository.MNORepository

class SaveMNOUseCase(private val mnoRepository: MNORepository) {

    suspend fun add(item: MNOData): Boolean {
        return mnoRepository.add(item)
    }

    suspend fun delete(item: MNOData): Boolean {
        return mnoRepository.delete(item)
    }
}