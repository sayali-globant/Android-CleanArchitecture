package com.marvel.data.characters.api

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import retrofit2.Response
import javax.inject.Inject


class CharactersApiHelperImpl @Inject constructor(
    private val apiService: CharactersApiService
) : CharactersApiHelper {

    override suspend fun getCharacters(request: CharactersRequest): Response<MarvelCharacterResponse> =
        apiService.getMarvelCharacters(
            request.ts,
            request.apiKey,
            request.hashKey
        )
}
