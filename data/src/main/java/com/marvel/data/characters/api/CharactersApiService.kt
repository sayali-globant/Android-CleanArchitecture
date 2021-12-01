package com.marvel.data.characters.api

import com.marvel.data.characters.model.MarvelCharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CharactersApiService {

    @GET("public/characters")
    suspend fun getMarvelCharacters(
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Response<MarvelCharacterResponse>
}
