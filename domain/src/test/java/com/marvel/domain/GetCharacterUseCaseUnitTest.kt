package com.marvel.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.characters.GetCharactersUseCase
import com.marvel.domain.usecase.characters.GetCharactersUseCaseImpl
import com.marvel.domain.usecase.repository.CharactersRepository
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

//@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class GetCharacterUseCaseUnitTest {

    @get: Rule
    val mRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()

    private lateinit var mCharacterUseCase: GetCharactersUseCase

    @Mock
    lateinit var mCharacterRepository: CharactersRepository

    @Mock
    private lateinit var mCharactersRequest: CharactersRequestModel


    @Before
    fun setUp() {
        mCharacterUseCase = GetCharactersUseCaseImpl(mCharacterRepository)
    }

    private val mCharactersList = listOf(
        CharacterModel(
            id = 1,
            name = "Iron Man",
            description = "Tony Stark",
            thumbnail = CharacterThumbnail("iron_man", "jpg")
        ),
        CharacterModel(
            id = 2,
            name = "Captain America",
            description = "Steve Rogers",
            thumbnail = CharacterThumbnail("captain_america", "jpg")
        )
    )


    @Rule
    @JvmField
    val injectMocks = TestRule { statement, _ ->
        MockitoAnnotations.initMocks(this)
        statement
    }

    @Test
    fun getCharacters_Success() {
        mTestCoroutineScope.runBlockingTest {
            whenever(mCharacterRepository.getCharacters(mCharactersRequest)).thenReturn(
                ApiState.success(
                    mCharactersList
                )
            )
            val result = mCharacterUseCase.getCharacters(mCharactersRequest)

            val isSuccess = result.status == Status.SUCCESS

            verify(mCharacterRepository).getCharacters(mCharactersRequest)

            Assert.assertTrue(isSuccess)
            Assert.assertEquals(mCharactersList, result.data)

        }
    }

    @Test
    fun getCharacters_Error() {
        mTestCoroutineScope.runBlockingTest {
            whenever(mCharacterRepository.getCharacters(mCharactersRequest)).thenReturn(
                ApiState.error("401", null)
            )

            val result = mCharacterUseCase.getCharacters(mCharactersRequest)

            verify(mCharacterRepository).getCharacters(mCharactersRequest)

            Assert.assertFalse(result.status == Status.SUCCESS)
            Assert.assertEquals(null, result.data)
        }
    }
}
