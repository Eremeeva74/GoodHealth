package com.eremeeva.domain.usecase

import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.repository.MNORepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetMNOUseCaseTest {

    val mnoRepository = mock<MNORepository>()

    @Test
    fun test1() {

        val usecase = GetMNOUseCase(mnoRepository = mnoRepository)

        runBlocking{
            Mockito.`when`(mnoRepository.getAll()).thenReturn(listOf(
                MNOData(1, "2025-06-15", 1.0, 2.0, 3.0),
            ))

            val actual = usecase.getAll()
            val expected = listOf(MNOData(1, "2025-06-15", 1.0, 2.0, 3.0))
            Assertions.assertEquals(expected, actual)
        }
    }
}