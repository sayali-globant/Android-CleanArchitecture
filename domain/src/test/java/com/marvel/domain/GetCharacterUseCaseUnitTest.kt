package com.marvel.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.Data
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.Thumbnail
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.characters.repository.CharacterRepository
import com.marvel.domain.usecase.characters.GetCharactersUseCase
import com.marvel.domain.usecase.characters.GetCharactersUseCaseImpl
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

//@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class GetCharacterUseCaseUnitTest {

    @get: Rule
    val mRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()

    private lateinit var mCharacterUseCase: GetCharactersUseCase

    @Mock
    lateinit var mCharacterRepository: CharacterRepository

    @Mock
    private lateinit var mCharactersRequest: CharactersRequest


    @Before
    fun setUp() {
        mCharacterUseCase = GetCharactersUseCaseImpl(mCharacterRepository)
    }

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
    fun getCharacters_Success() {
        mTestCoroutineScope.runBlockingTest {
            whenever(mCharacterRepository.getCharacters(mCharactersRequest)).thenReturn(
                Response.success(
                    mCharacterSuccessResponse
                )
            )
            val result = mCharacterUseCase.getCharacters(mCharactersRequest)

            val isSuccess = result.isSuccessful

            verify(mCharacterRepository).getCharacters(mCharactersRequest)

            Assert.assertTrue(isSuccess)
            Assert.assertEquals(mCharactersList, result.body()?.mainData?.results)

        }
    }

    @Test
    fun getCharacters_Error() {
        mTestCoroutineScope.runBlockingTest {
            val responseBody: ResponseBody =
                "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(mCharacterRepository.getCharacters(mCharactersRequest)).thenReturn(
                Response.error(
                    401,
                    responseBody
                )
            )

            val result = mCharacterUseCase.getCharacters(mCharactersRequest)

            verify(mCharacterRepository).getCharacters(mCharactersRequest)

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(null, result.body()?.mainData?.results)
        }
    }
}
