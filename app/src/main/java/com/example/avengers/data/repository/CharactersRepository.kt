package com.example.avengers.data.repository

import com.example.avengers.domain.model.AvengerCharacterResponse
import com.example.avengers.domain.model.request.CharactersRequest

interface CharactersRepository {
   suspend fun getCharacters(charactersRequest: CharactersRequest): AvengerCharacterResponse
}