package com.marvel.data.details.api

import com.marvel.data.BuildConfig
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.getMd5
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CharacterDetailsApiService {

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterDetails(
        @Path(value = "characterId") characterId: String,
        @Query("apikey") apiKey: String = BuildConfig.PUBLIC_API_KEY,
        @Query("ts") timeStamp: Long = System.currentTimeMillis(),
        @Query("hash") apiHash: String = getMd5(timeStamp)
    ): Response<MarvelCharacterResponse>
}
