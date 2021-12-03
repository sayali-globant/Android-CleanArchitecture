package com.marvel.data.details.api

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import retrofit2.Response
import javax.inject.Inject


class CharacterDetailsApiHelperImpl @Inject constructor(
    private val apiService: CharacterDetailsApiService
) : CharacterDetailsApiHelper {
    override suspend fun getCharacterDetails(charactersRequest: CharactersRequest): Response<MarvelCharacterResponse> =
        apiService.getCharacterDetails(
            charactersRequest.id,
            charactersRequest.apiKey,
            charactersRequest.ts,
            charactersRequest.hashKey
        )
}
