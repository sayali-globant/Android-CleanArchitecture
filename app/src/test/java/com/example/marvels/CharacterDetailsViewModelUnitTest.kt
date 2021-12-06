package com.example.marvels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvels.presentation.characterdetail.CharacterDetailViewModel
import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCaseImpl
import com.marvel.domain.usecase.repository.CharacterDetailsRepository
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
class CharacterDetailsViewModelUnitTest {

    @get: Rule
    val mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineRule = TestCoroutinesRule()

    @Mock
    lateinit var getCharacterDetailsUseCase: GetCharacterDetailsUseCaseImpl

    @Mock
    lateinit var characterDetailsRepository: CharacterDetailsRepository

    lateinit var charachtersViewModel: CharacterDetailViewModel


    private var mCharacterRequest = CharactersRequestModel(id = "1234")

    private val mCharacter =
        CharacterModel(
            id = 1,
            name = "Iron Man",
            description = "Tony Stark",
            thumbnail = CharacterThumbnail("iron_man", "jpg")
        )

    @Rule
    @JvmField
    val injectMocks = TestRule { statement, _ ->
        MockitoAnnotations.initMocks(this)
        statement
    }

    @Before
    fun start() {
        getCharacterDetailsUseCase = GetCharacterDetailsUseCaseImpl(characterDetailsRepository)
        charachtersViewModel =
            CharacterDetailViewModel(getCharacterDetailsUseCase)
    }

    @Test
    fun testApiGetCharacterDetails_Success() {
        mTestCoroutineRule.runBlockingTest {
            whenever(getCharacterDetailsUseCase.getCharacterDetails(mCharacterRequest)).thenReturn(
                ApiState.success(mCharacter)
            )
            val result = charachtersViewModel.getCharacterDetails(mCharacterRequest)
            Assert.assertNotNull(result)
        }
    }

    @Test
    fun testApiGetCharacterDetails_Error() {
        mTestCoroutineRule.runBlockingTest {


            whenever(getCharacterDetailsUseCase.getCharacterDetails(mCharacterRequest)).thenReturn(
                ApiState.error(
                    "401", null
                )
            )
            val result = charachtersViewModel.getCharacterDetails(mCharacterRequest)
            Assert.assertNotNull(result)

        }
    }
}
