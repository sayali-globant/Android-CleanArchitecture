package com.marvel.data.characters.repository

import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.source.characterlist.CharactersDataSource
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.repository.CharactersRepository
import javax.inject.Inject


class CharacterRepositoryImpl @Inject constructor(private val characterListDataSource: CharactersDataSource) :
    CharactersRepository {

    override suspend fun getCharacters(charactersRequest: CharactersRequestModel) =
        characterListDataSource.getCharacters(
            CharactersRequest(
                charactersRequest.ts, charactersRequest.apiKey,
                charactersRequest.hashKey
            )
        )
}
