package com.marvel.domain.usecase.repository

import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharactersRequestModel


interface CharactersRepository {
    suspend fun getCharacters(charactersRequest: CharactersRequestModel): ApiState<List<CharacterModel>>
}
