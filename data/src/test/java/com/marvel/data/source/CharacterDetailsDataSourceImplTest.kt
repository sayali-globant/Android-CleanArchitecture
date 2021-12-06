package com.marvel.data.source

import com.marvel.data.characters.api.CharactersApiService
import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.Data
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.Thumbnail
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.mapper.characterdetails.CharacterDetailsResponseMapper
import com.marvel.data.mapper.characterdetails.CharacterDetailsResponseMapperImpl
import com.marvel.data.source.characterdetails.CharacterDetailsDataSource
import com.marvel.data.source.characterdetails.CharacterDetailsDataSourceImpl
import com.marvel.domain.model.CharactersRequestModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class CharacterDetailsDataSourceImplTest {

    @Mock
    lateinit var mCharacterApi: CharactersApiService

    private lateinit var mCharacterDetailsResponseMapper: CharacterDetailsResponseMapper
    private lateinit var mCharacterDetailsDataSource: CharacterDetailsDataSource
    private var mCharactersRequest = CharactersRequestModel(id = "1231")
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
    private val mMarvelCharacterResponse = MarvelCharacterResponse(mainData = Data(mCharactersList))

    @Before
    fun setUp() {
        //Used for initiation of Mockk
        mCharacterApi = Mockito.mock(CharactersApiService::class.java)
        mCharacterDetailsResponseMapper = CharacterDetailsResponseMapperImpl()
        mCharacterDetailsDataSource =
            CharacterDetailsDataSourceImpl(mCharacterApi, mCharacterDetailsResponseMapper)
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetCharactersDetails() = runBlocking {
        whenever(
            mCharacterApi.getCharacterDetails(
                mCharactersRequest.id,
                mCharactersRequest.apiKey,
                mCharactersRequest.ts,
                mCharactersRequest.hashKey
            )
        ).thenReturn(
            Response.success(mMarvelCharacterResponse)
        )
        val serviceResult = mCharacterApi.getCharacterDetails(
            mCharactersRequest.id,
            mCharactersRequest.apiKey,
            mCharactersRequest.ts,
            mCharactersRequest.hashKey
        )
        whenever(serviceResult.isSuccessful).thenReturn(
            true
        )
        val result = mCharacterDetailsDataSource.getCharacterDetails(
            CharactersRequest(
                id = mCharactersRequest.id,
                apiKey = mCharactersRequest.apiKey,
                ts = mCharactersRequest.ts,
                hashKey = mCharactersRequest.hashKey
            )
        )
        Assert.assertEquals(serviceResult.isSuccessful, true)
        Assert.assertNotNull(result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetCharacterDetailsError() = runBlocking {
        whenever(
            mCharacterApi.getCharacterDetails(
                mCharactersRequest.id,
                mCharactersRequest.apiKey,
                mCharactersRequest.ts,
                mCharactersRequest.hashKey
            )
        ).thenReturn(
            Response.success(
                mMarvelCharacterResponse
            )
        )
        val serviceResult = mCharacterApi.getCharacterDetails(
            mCharactersRequest.id,
            mCharactersRequest.apiKey,
            mCharactersRequest.ts,
            mCharactersRequest.hashKey
        )
        whenever(serviceResult.isSuccessful).thenReturn(
            false
        )
        val result = mCharacterDetailsDataSource.getCharacterDetails(
            CharactersRequest(
                id = mCharactersRequest.id,
                apiKey = mCharactersRequest.apiKey,
                ts = mCharactersRequest.ts,
                hashKey = mCharactersRequest.hashKey
            )
        )
        Assert.assertEquals(!serviceResult.isSuccessful, false)
        Assert.assertNotNull(result)
    }
}
