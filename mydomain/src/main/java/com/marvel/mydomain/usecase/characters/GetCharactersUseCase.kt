package com.marvel.mydomain.usecase.characters

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import retrofit2.Response


interface GetCharactersUseCase {
    suspend fun getCharacters(request: CharactersRequest): Response<MarvelCharacterResponse>
}
