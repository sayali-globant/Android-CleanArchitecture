package com.example.avengers.data.repository

import com.example.avengers.data.service.AvengerService
import com.example.avengers.domain.model.AvengerCharacterResponse
import com.example.avengers.domain.model.request.CharactersRequest

class CharacterRepositoryImpl(private val avengerService: AvengerService) : CharactersRepository {
    override suspend fun getCharacters(charactersRequest: CharactersRequest): AvengerCharacterResponse {
        return avengerService.getMarvelCharacters(charactersRequest.ts,
        charactersRequest.apiKey,
        charactersRequest.hashKey,
        charactersRequest.limit,
        charactersRequest.offset)
    }
}