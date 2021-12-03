package com.marvel.domain.usecase.details

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import retrofit2.Response


interface GetCharacterDetailsUseCase {
    suspend fun getCharacterDetails(charactersRequest: CharactersRequest): Response<MarvelCharacterResponse>
}
