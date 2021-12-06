package com.marvel.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.Data
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.Thumbnail
import com.marvel.data.characters.model.request.CharactersRequest
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response


@ExperimentalCoroutinesApi
class CharacterDetailsRepositoryUnitTest {

    @get: Rule
    val mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()

    private lateinit var mApiHelper: CharacterDetailsApiHelper

    private var mCharacterRequest = CharactersRequest(id = "1234")

    @Mock
    lateinit var apiService: CharacterApiService

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

    @Before
    fun setUp() {
        mApiHelper = CharacterDetailsApiHelperImpl(apiService)
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
            whenever(
                apiService.getCharacterDetails(
                    "1234",
                    mCharacterRequest.apiKey,
                    mCharacterRequest.ts,
                    mCharacterRequest.hashKey
                )
            ).thenReturn(Response.success(mCharacterSuccessResponse))
            val result = mApiHelper.getCharacterDetails(mCharacterRequest)
            verify(apiService).getCharacterDetails(
                "1234",
                mCharacterRequest.apiKey,
                mCharacterRequest.ts,
                mCharacterRequest.hashKey
            )
            val isSuccess = result.isSuccessful


            Assert.assertTrue(isSuccess)
            Assert.assertEquals(mCharacterSuccessResponse, result.body())
        }
    }

    @Test
    fun getCharacterDetails_Error() {
        mTestCoroutineScope.runBlockingTest {
            val responseBody: ResponseBody =
                "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(
                apiService.getCharacterDetails(
                    "1234",
                    mCharacterRequest.apiKey,
                    mCharacterRequest.ts,
                    mCharacterRequest.hashKey
                )
            ).thenReturn(Response.error(401, responseBody))

            val result = mApiHelper.getCharacterDetails(mCharacterRequest)

            verify(apiService).getCharacterDetails(
                "1234",
                mCharacterRequest.apiKey,
                mCharacterRequest.ts,
                mCharacterRequest.hashKey
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(result.errorBody(), responseBody)
        }
    }
}
