package com.marvel.domain.usecase.repository

import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharactersRequestModel


/**
 * This is CharacterDetails repository interface
 */
interface CharacterDetailsRepository {
    suspend fun getCharacterDetails(charactersRequest: CharactersRequestModel): ApiState<CharacterModel>
}
