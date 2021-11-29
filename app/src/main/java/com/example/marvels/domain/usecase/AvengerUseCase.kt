package com.example.marvels.domain.usecase

import com.example.marvels.data.entity.AvengerCharacterResponse
import com.example.marvels.data.entity.request.CharactersRequest
import com.example.marvels.data.repository.CharactersRepository
import com.example.marvels.domain.usecase.base.UseCase

class AvengerUseCase constructor(
    private val mCharactersRepository: CharactersRepository
) : UseCase<AvengerCharacterResponse, CharactersRequest>() {

    override suspend fun run(params: CharactersRequest?): AvengerCharacterResponse{
        return mCharactersRepository.getCharacters(params!!)
    }



}