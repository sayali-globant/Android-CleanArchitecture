package com.example.avengers.domain.usecase

import com.example.avengers.domain.model.AvengerCharacterResponse
import com.example.avengers.domain.model.request.CharactersRequest
import com.example.avengers.data.repository.CharactersRepository
import com.example.avengers.domain.usecase.base.UseCase

class AvengerUseCase constructor(
    private val charactersrepository: CharactersRepository
) : UseCase<AvengerCharacterResponse, CharactersRequest>() {

    override suspend fun run(params: CharactersRequest?): AvengerCharacterResponse{
        return charactersrepository.getCharacters(params!!)
    }



}