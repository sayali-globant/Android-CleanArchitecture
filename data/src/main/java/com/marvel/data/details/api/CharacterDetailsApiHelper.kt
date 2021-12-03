package com.marvel.data.details.api

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import retrofit2.Response


interface CharacterDetailsApiHelper {
    suspend fun getCharacterDetails(charactersRequest: CharactersRequest): Response<MarvelCharacterResponse>
}
