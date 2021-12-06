package com.marvel.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.details.repository.CharacterDetailsRepositoryImpl
import com.marvel.data.source.characterdetails.CharacterDetailsDataSource
import com.marvel.domain.ApiState
import com.marvel.domain.Status
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.repository.CharacterDetailsRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class CharacterDetailsRepositoryUnitTest {

    @get: Rule
    val mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()


    private var mCharactersRequest = CharactersRequestModel(id = "1234")

    @Mock
    lateinit var mCharacterDetailsDataSource: CharacterDetailsDataSource
    private lateinit var mCharacterDetailsRepository: CharacterDetailsRepository

    @Before
    fun setUp() {
        //Used for initiation of Mockk
        mCharacterDetailsDataSource = Mockito.mock(CharacterDetailsDataSource::class.java)
        mCharacterDetailsRepository = CharacterDetailsRepositoryImpl(mCharacterDetailsDataSource)
    }

    private val mCharactersDetail = CharacterModel(
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

    @Test
    fun getCharacterDetails_Success() {

        mTestCoroutineScope.runBlockingTest {
            whenever(
                mCharacterDetailsDataSource.getCharacterDetails(
                    CharactersRequest(
                        mCharactersRequest.ts,
                        mCharactersRequest.apiKey,
                        mCharactersRequest.hashKey,
                        mCharactersRequest.id
                    )
                )
            ).thenReturn(
                ApiState.success(
                    mCharactersDetail
                )
            )
            val result = mCharacterDetailsRepository.getCharacterDetails(mCharactersRequest)
            Assert.assertTrue(result.status == Status.SUCCESS)
        }
    }

    @Test
    fun getCharacterDetails_Error() {
        mTestCoroutineScope.runBlockingTest {
            mTestCoroutineScope.runBlockingTest {
                whenever(
                    mCharacterDetailsDataSource.getCharacterDetails(
                        CharactersRequest(
                            mCharactersRequest.ts,
                            mCharactersRequest.apiKey,
                            mCharactersRequest.hashKey,
                            mCharactersRequest.id
                        )
                    )
                ).thenReturn(
                    ApiState.error(
                        "401", null
                    )
                )
                val result = mCharacterDetailsRepository.getCharacterDetails(mCharactersRequest)
                Assert.assertTrue(result.status == Status.ERROR)
            }
        }
    }
}
