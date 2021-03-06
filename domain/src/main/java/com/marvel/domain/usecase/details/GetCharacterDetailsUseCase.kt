package com.marvel.domain.usecase.details

import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharactersRequestModel


interface GetCharacterDetailsUseCase {
    suspend fun getCharacterDetails(charactersRequest: CharactersRequestModel): ApiState<CharacterModel>
}
