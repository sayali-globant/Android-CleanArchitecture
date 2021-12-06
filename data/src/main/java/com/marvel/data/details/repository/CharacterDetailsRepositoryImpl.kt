package com.marvel.data.details.repository

import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.source.characterdetails.CharacterDetailsDataSource
import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.repository.CharacterDetailsRepository
import javax.inject.Inject


class CharacterDetailsRepositoryImpl @Inject constructor(private val characterDetailDataSource: CharacterDetailsDataSource) :
    CharacterDetailsRepository {
    override suspend fun getCharacterDetails(charactersRequest: CharactersRequestModel) =
        characterDetailDataSource.getCharacterDetails(
            CharactersRequest(
                charactersRequest.ts,
                charactersRequest.apiKey,
                charactersRequest.hashKey,
                charactersRequest.id
            )
        )

}
