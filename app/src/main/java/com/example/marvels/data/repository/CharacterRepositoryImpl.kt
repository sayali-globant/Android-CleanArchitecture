package com.example.marvels.data.repository

import com.example.marvels.data.entity.AvengerCharacterResponse
import com.example.marvels.data.service.AvengerService
import com.example.marvels.data.entity.request.CharactersRequest

class CharacterRepositoryImpl(private val avengerService: AvengerService) : CharactersRepository {
    override suspend fun getCharacters(charactersRequest: CharactersRequest): AvengerCharacterResponse {
        return avengerService.getMarvelCharacters(charactersRequest.ts,
        charactersRequest.apiKey,
        charactersRequest.hashKey,
        charactersRequest.limit,
        charactersRequest.offset)
    }
}