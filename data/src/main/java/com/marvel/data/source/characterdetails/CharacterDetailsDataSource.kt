package com.marvel.data.source.characterdetails

import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel


interface CharacterDetailsDataSource {
    suspend fun getCharacterDetails(charactersRequest: CharactersRequest): ApiState<CharacterModel>
}
