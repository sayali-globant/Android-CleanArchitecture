package com.marvel.domain.usecase.details

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.details.repository.CharacterDetailsRepository
import retrofit2.Response
import javax.inject.Inject


class GetCharacterDetailsUseCaseImpl @Inject constructor(
    private val repository: CharacterDetailsRepository
) : GetCharacterDetailsUseCase {
    override suspend fun getCharacterDetails(charactersRequest: CharactersRequest): Response<MarvelCharacterResponse> =
        repository.getCharacterDetails(charactersRequest)
}
