package com.example.marvels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.marvels.presentation.characterdetail.CharacterDetailViewModel
import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.Data
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.Thumbnail
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.domain.ApiState
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCase
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
class CharacterDetailsViewModelUnitTest {

    @get: Rule
    val mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineRule = TestCoroutinesRule()

    @Mock
    private lateinit var mObserver: Observer<ApiState<MarvelCharacterResponse>>

    @Mock
    private lateinit var mUseCase: GetCharacterDetailsUseCase


    private var mCharaRequest = CharactersRequest(id = "1234")

    private val mCharactersList = listOf(
        CharacterDetail(
            id = 1,
            name = "Iron Man",
            description = "Tony Stark",
            thumbnail = Thumbnail("iron_man", "jpg")
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
    fun testApiGetCharacterDetails_Success() {
        mTestCoroutineRule.runBlockingTest {
            whenever(mUseCase.getCharacterDetails(mCharaRequest)).thenReturn(
                Response.success(
                    mCharacterSuccessResponse
                )
            )
            val result = mUseCase.getCharacterDetails(mCharaRequest)

            val detailsViewModel = CharacterDetailViewModel(mUseCase)
            detailsViewModel.mCharacterDetails.observeForever(mObserver)
            detailsViewModel.getCharacterDetails(mCharaRequest)

            verify(mUseCase).getCharacterDetails(mCharaRequest)

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals(mCharacterSuccessResponse, result.body())
        }
    }

    @Test
    fun testApiGetCharacterDetails_Error() {
        mTestCoroutineRule.runBlockingTest {
            val responseBody: ResponseBody =
                "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(mUseCase.getCharacterDetails(mCharaRequest)).thenReturn(
                Response.error(
                    401,
                    responseBody
                )
            )

            val result = mUseCase.getCharacterDetails(mCharaRequest)

            val detailsViewModel = CharacterDetailViewModel(mUseCase)
            detailsViewModel.mCharacterDetails.observeForever(mObserver)
            detailsViewModel.getCharacterDetails(mCharaRequest)

            verify(mUseCase).getCharacterDetails(mCharaRequest)

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(result.errorBody(), responseBody)
        }
    }
}
