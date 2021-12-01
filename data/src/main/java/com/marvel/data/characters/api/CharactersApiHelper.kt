package com.marvel.data.characters.api

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import retrofit2.Response


interface CharactersApiHelper {
    suspend fun getCharacters(request: CharactersRequest): Response<MarvelCharacterResponse>
}
