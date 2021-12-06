package com.marvel.domain.usecase.characters

import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharactersRequestModel


interface GetCharactersUseCase {
    suspend fun getCharacters(request: CharactersRequestModel): ApiState<List<CharacterModel>>
}
