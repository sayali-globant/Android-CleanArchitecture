package com.marvel.data.source

import com.marvel.data.characters.api.CharactersApiService
import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.Data
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.Thumbnail
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.mapper.charcters.CharactersResponseMapper
import com.marvel.data.mapper.charcters.CharactersResponseMapperImpl
import com.marvel.data.source.characterlist.CharactersDataSourceImpl
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
class CharactersSourceImplTest {

    @Mock
    lateinit var mCharacterApi: CharactersApiService

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

    @Mock
    lateinit var mCharacterListResponseMapper: CharactersResponseMapper
    private lateinit var mCharacterListDataSource: CharactersDataSourceImpl

    private var mCharactersRequest = CharactersRequestModel()

    @Before
    fun setUp() {
        //Used for initiation of Mockk
        mCharacterApi = Mockito.mock(CharactersApiService::class.java)
        mCharacterListResponseMapper = CharactersResponseMapperImpl()
        MockitoAnnotations.initMocks(this)
        mCharacterListDataSource =
            CharactersDataSourceImpl(mCharacterApi, mCharacterListResponseMapper)
    }

    @Test
    fun testGetCharacters() = runBlocking {
        whenever(
            mCharacterApi.getMarvelCharacters(
                mCharactersRequest.ts,
                mCharactersRequest.apiKey,
                mCharactersRequest.hashKey
            )
        ).thenReturn(
            Response.success(mMarvelCharacterResponse)
        )
        val serviceResult = mCharacterApi.getMarvelCharacters(
            mCharactersRequest.ts,
            mCharactersRequest.apiKey,
            mCharactersRequest.hashKey
        )
        whenever(serviceResult.isSuccessful).thenReturn(
            true
        )
        val result = mCharacterListDataSource.getCharacters(
            CharactersRequest(
                ts = mCharactersRequest.ts,
                apiKey = mCharactersRequest.apiKey,
                hashKey = mCharactersRequest.hashKey
            )
        )
        Assert.assertEquals(serviceResult.isSuccessful, true)
        Assert.assertNotNull(result)
    }

    @Test
    fun testGetCharactersError() = runBlocking {
        whenever(
            mCharacterApi.getMarvelCharacters(
                mCharactersRequest.ts,
                mCharactersRequest.apiKey,
                mCharactersRequest.hashKey
            )
        ).thenReturn(
            Response.success(mMarvelCharacterResponse)
        )
        val serviceResult = mCharacterApi.getMarvelCharacters(
            mCharactersRequest.ts,
            mCharactersRequest.apiKey,
            mCharactersRequest.hashKey
        )
        whenever(serviceResult.isSuccessful).thenReturn(
            false
        )
        val result = mCharacterListDataSource.getCharacters(
            CharactersRequest(
                ts = mCharactersRequest.ts,
                apiKey = mCharactersRequest.apiKey,
                hashKey = mCharactersRequest.hashKey
            )
        )
        Assert.assertEquals(!serviceResult.isSuccessful, false)
        Assert.assertNotNull(result)
    }
}
