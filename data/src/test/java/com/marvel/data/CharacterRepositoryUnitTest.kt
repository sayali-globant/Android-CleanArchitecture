package com.marvel.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.characters.repository.CharacterRepositoryImpl
import com.marvel.data.source.characterlist.CharactersDataSource
import com.marvel.domain.ApiState
import com.marvel.domain.Status
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail
import com.marvel.domain.model.CharactersRequestModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class CharacterRepositoryUnitTest {

    @get: Rule
    val mRule = InstantTaskExecutorRule()

    @get: Rule
    val mTestCoroutineScope = TestCoroutinesRule()


    private var mCharactersRequest = CharactersRequestModel()

    /* @MockK
     lateinit var characterListDataSource: CharacterListDataSource
     private lateinit var characterListRepository: CharacterListRepositoryImpl
 */
    /*@Before
    fun setUp() {
        //Used for initiation of Mockk
        characterListDataSource = Mockito.mock(CharacterListDataSource::class.java)
        MockitoAnnotations.initMocks(this)
        characterListRepository = CharacterListRepositoryImpl(characterListDataSource)
    }*/


    @Mock
    lateinit var mCharacterListDataSource: CharactersDataSource
    private lateinit var mCharacterListRepository: CharacterRepositoryImpl

    @Before
    fun setUp() {
        //Used for initiation of Mockk
        mCharacterListDataSource = Mockito.mock(CharactersDataSource::class.java)
        MockitoAnnotations.initMocks(this)
        mCharacterListRepository = CharacterRepositoryImpl(mCharacterListDataSource)
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
    fun getCharacterSuccess() {
        mTestCoroutineScope.runBlockingTest {
            whenever(
                mCharacterListDataSource.getCharacters(
                    CharactersRequest(
                        mCharactersRequest.ts,
                        mCharactersRequest.apiKey,
                        mCharactersRequest.hashKey,
                        mCharactersRequest.id
                    )
                )
            ).thenReturn(
                ApiState.success(
                    mCharactersList
                )
            )
            val result = mCharacterListRepository.getCharacters(mCharactersRequest)
            Assert.assertTrue(result.status == Status.SUCCESS)
        }
    }

    @Test
    fun getCharactersError() {
        mTestCoroutineScope.runBlockingTest {
            whenever(
                mCharacterListDataSource.getCharacters(
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
            val result = mCharacterListRepository.getCharacters(mCharactersRequest)
            Assert.assertTrue(result.status == Status.ERROR)
        }
    }
}
