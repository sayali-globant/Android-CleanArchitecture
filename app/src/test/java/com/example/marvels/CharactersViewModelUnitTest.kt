package com.example.marvels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.marvels.presentation.characters.CharactersViewModel
import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.Data
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.Thumbnail
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.domain.ApiState
import com.marvel.domain.usecase.characters.GetCharactersUseCase
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelUnitTest {

    @get: Rule
    val mRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()

    @Mock
    lateinit var mCharactersRequest: CharactersRequest

    @Mock
    private lateinit var mObserver: Observer<ApiState<MarvelCharacterResponse>>

    @Mock
    private lateinit var mUseCase: GetCharactersUseCase

    private val mCharactersList = listOf(
        CharacterDetail(
            id = 1,
            name = "Iron Man",
            description = "Tony Stark",
            thumbnail = Thumbnail("iron_man", "jpg")
        ),
        CharacterDetail(
            id = 2,
            name = "Captain America",
            description = "Steve Rogers",
            thumbnail = Thumbnail("captain_america", "jpg")
        )
    )

    private val mCharacterSuccessResponse = MarvelCharacterResponse(
        code = 200,
        status = "Ok",
        mainData = Data(1, 20, 20, total = 100, results = mCharactersList)
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
                Response.success(
                    mCharacterSuccessResponse
                )
            )
            val result = mUseCase.getCharacters(mCharactersRequest)

            val charactersViewModel = CharactersViewModel(mUseCase)
            charactersViewModel.mCharactersResponse.observeForever(mObserver)
            charactersViewModel.getCharactersList(mCharactersRequest)

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals(mCharacterSuccessResponse, result.body())
        }
    }

    @Test
    fun testApiGetCharacter_Error() {
        mTestCoroutineScope.runBlockingTest {
            val responseBody: ResponseBody =
                "{}".toResponseBody("application/json".toMediaTypeOrNull())

            whenever(mUseCase.getCharacters(mCharactersRequest)).thenReturn(
                Response.error(
                    401,
                    responseBody
                )
            )
            val result = mUseCase.getCharacters(mCharactersRequest)

            val charactersViewModel = CharactersViewModel(mUseCase)
            charactersViewModel.mCharactersResponse.observeForever(mObserver)
            charactersViewModel.getCharactersList(mCharactersRequest)

            verify(mUseCase).getCharacters(mCharactersRequest)

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(result.errorBody(), responseBody)
        }

    }
}
