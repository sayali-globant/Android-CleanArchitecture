package com.marvel.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCase
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCaseImpl
import com.marvel.domain.usecase.repository.CharacterDetailsRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class GetCharacterDetailsUseCaseUnitTest {

    @get: Rule
    val mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()

    private lateinit var mUseCase: GetCharacterDetailsUseCase


    private var mCharactersRequest = CharactersRequestModel(id = "1234")

    @Mock
    private lateinit var mRepository: CharacterDetailsRepository

    private val mCharacterModel =
        CharacterModel(
            id = 1,
            name = "Iron Man",
            description = "Tony Stark",
            thumbnail = CharacterThumbnail("iron_man", "jpg")
        )


    @Before
    fun setUp() {
        mUseCase = GetCharacterDetailsUseCaseImpl(mRepository)
    }

    @Rule
    @JvmField
    val injectMocks = TestRule { statement, _ ->
        MockitoAnnotations.initMocks(this)
        statement
    }

    @Test
    fun getCharacterDetails_Success() {
        mTestCoroutineScope.runBlockingTest {
            whenever(mRepository.getCharacterDetails(mCharactersRequest)).thenReturn(
                ApiState.success(
                    mCharacterModel
                )
            )
            val result = mUseCase.getCharacterDetails(mCharactersRequest)
            val isSuccess = result.status == Status.SUCCESS

            verify(mRepository).getCharacterDetails(mCharactersRequest)

            Assert.assertTrue(isSuccess)
            Assert.assertEquals(mCharacterModel, result.data)
        }
    }

    @Test
    fun getCharacterDetails_Error() {
        mTestCoroutineScope.runBlockingTest {
            whenever(mRepository.getCharacterDetails(mCharactersRequest)).thenReturn(
                ApiState.error(
                    "401",
                    null
                )
            )

            val result = mUseCase.getCharacterDetails(mCharactersRequest)
            verify(mRepository).getCharacterDetails(mCharactersRequest)

            Assert.assertTrue(result.status == Status.ERROR)
            Assert.assertEquals(result.data, null)
        }
    }
}
