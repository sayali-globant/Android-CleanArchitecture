package com.marvel.mydomain.usecase.characters

import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.characters.repository.CharacterRepository
import javax.inject.Inject


class GetCharactersUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : GetCharactersUseCase {
    override suspend fun getCharacters(request: CharactersRequest) =
        repository.getCharacters(request)
}
