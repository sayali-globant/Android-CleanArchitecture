package com.marvel.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.data.characters.api.CharactersApiHelper
import com.marvel.data.characters.api.CharactersApiHelperImpl
import com.marvel.data.characters.api.CharactersApiService
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
class CharacterRepositoryUnitTest {

    @get: Rule
    val mRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()

    private lateinit var mApiHelper: CharactersApiHelper

    @Mock
    lateinit var mApiService: CharactersApiService

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

    @Before
    fun setUp() {
        mApiHelper = CharactersApiHelperImpl(mApiService)
    }

    @Mock
    lateinit var mCharactersRequest: CharactersRequest

    @Rule
    @JvmField
    val injectMocks = TestRule { statement, _ ->
        MockitoAnnotations.initMocks(this)
        statement
    }

    @Test
    fun getCharacterSuccess() {
        mTestCoroutineScope.runBlockingTest {
            whenever(
                mApiService.getMarvelCharacters(
                    mCharactersRequest.ts,
                    mCharactersRequest.apiKey,
                    mCharactersRequest.hashKey
                )
            ).thenReturn(Response.success(mCharacterSuccessResponse))

            val result = mApiHelper.getCharacters(mCharactersRequest)

            val isSuccess = result.isSuccessful

            verify(mApiService).getMarvelCharacters(
                mCharactersRequest.ts,
                mCharactersRequest.apiKey,
                mCharactersRequest.hashKey
            )

            Assert.assertTrue(isSuccess)
            Assert.assertEquals(mCharactersList, result.body()?.mainData?.results)
        }
    }

    @Test
    fun getCharactersError() {
        mTestCoroutineScope.runBlockingTest {
            val responseBody: ResponseBody =
                "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(
                mApiService.getMarvelCharacters(
                    mCharactersRequest.ts,
                    mCharactersRequest.apiKey,
                    mCharactersRequest.hashKey
                )
            ).thenReturn(Response.error(401, responseBody))

            val result = mApiHelper.getCharacters(mCharactersRequest)

            verify(mApiService).getMarvelCharacters(
                mCharactersRequest.ts,
                mCharactersRequest.apiKey,
                mCharactersRequest.hashKey
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(null, result.body()?.mainData?.results)
        }
    }
}
