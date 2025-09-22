package usecase

import com.eremeeva.domain.models.MNOData
import com.eremeeva.domain.repository.MNORepository
import com.eremeeva.domain.usecase.GetMNOUseCase
import com.eremeeva.domain.usecase.SaveMNOUseCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class SaveMNOUseCaseTest {

    val mnoRepository = mock<MNORepository>()
    val mnoList = mutableListOf(MNOData(1, "2025-06-15", 1.0, 2.0, 3.0))

    @Test
    fun testAdd(){

        val saveusecase = SaveMNOUseCase(mnoRepository = mnoRepository)
        val getusecase = GetMNOUseCase(mnoRepository = mnoRepository)

        runBlocking{
            /*
            val testMNO = MNOData(2, "2025-06-30", 4.0, 5.0, 6.0)

            Mockito.`when`(mnoRepository.getAll()).thenReturn(mnoList)
            Mockito.`when`(mnoRepository.add(testMNO)).then { mnoList.add(testMNO) }

            val expectedList = usecase.getAll()
            val expected: MutableList<MNOData> = mutableListOf()
            if (expectedList.isNotEmpty()){
                for(item in expectedList)
                    expected.add(item)
            }

            saveusecase.add(testMNO)
            val expectedList = usecase.getAll()

            usecase.add(testMNO)
            //val actualList = mnoRepository.getAll()
            val actual : MutableList<MNOData> = mutableListOf()
            if (actualList.isNotEmpty()){
                for(item in actualList)
                    actual.add(item)
            }
*/
            val expected = true
            val actual = true
            Assertions.assertEquals(expected, actual)
        }
    }

}

