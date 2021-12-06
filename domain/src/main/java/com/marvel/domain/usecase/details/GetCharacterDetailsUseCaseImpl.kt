package com.marvel.domain.usecase.details

import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.repository.CharacterDetailsRepository
import javax.inject.Inject


class GetCharacterDetailsUseCaseImpl @Inject constructor(
    private val repository: CharacterDetailsRepository
) : GetCharacterDetailsUseCase {
    override suspend fun getCharacterDetails(charactersRequest: CharactersRequestModel) =
        repository.getCharacterDetails(charactersRequest)
}

