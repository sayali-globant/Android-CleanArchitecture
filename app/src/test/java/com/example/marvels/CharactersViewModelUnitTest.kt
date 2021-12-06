package com.example.marvels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvels.presentation.characters.CharactersViewModel
import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.characters.GetCharactersUseCaseImpl
import com.marvel.domain.usecase.repository.CharactersRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelUnitTest {

    @get: Rule
    val mRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()

    @Mock
    lateinit var mCharactersRequest: CharactersRequestModel

    @Mock
    lateinit var mCharacterListRepository: CharactersRepository

    lateinit var mCharacterListsViewModel: CharactersViewModel

    @Mock
    private lateinit var mUseCase: GetCharactersUseCaseImpl


    @Before
    fun start() {
        mUseCase = GetCharactersUseCaseImpl(mCharacterListRepository)
        mCharacterListsViewModel =
            CharactersViewModel(mUseCase)
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
    fun testApiGetCharacter_Success() {
        mTestCoroutineScope.runBlockingTest {
            whenever(mUseCase.getCharacters(mCharactersRequest)).thenReturn(
                ApiState.success(mCharactersList)
            )
            val result = mCharacterListsViewModel.getCharactersList(mCharactersRequest)
            Assert.assertNotNull(result)
        }
    }

    @Test
    fun testApiGetCharacter_Error() {
        mTestCoroutineScope.runBlockingTest {
            whenever(mUseCase.getCharacters(mCharactersRequest)).thenReturn(
                ApiState.error(
                    "401 ", null
                )
            )
            val result = mCharacterListsViewModel.getCharactersList(mCharactersRequest)
            Assert.assertNotNull(result)

        }

    }
}
