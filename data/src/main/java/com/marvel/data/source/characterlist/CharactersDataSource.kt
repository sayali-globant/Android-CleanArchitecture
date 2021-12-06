package com.marvel.data.source.characterlist

import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel


interface CharactersDataSource {
    suspend fun getCharacters(charactersRequest: CharactersRequest): ApiState<List<CharacterModel>>
}
