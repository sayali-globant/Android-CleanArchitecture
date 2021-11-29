package com.example.marvels.data.repository

import com.example.marvels.data.entity.AvengerCharacterResponse
import com.example.marvels.data.entity.request.CharactersRequest

interface CharactersRepository {
   suspend fun getCharacters(charactersRequest: CharactersRequest): AvengerCharacterResponse
}