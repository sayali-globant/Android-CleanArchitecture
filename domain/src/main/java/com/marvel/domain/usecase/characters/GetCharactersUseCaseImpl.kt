package com.marvel.domain.usecase.characters

import com.marvel.domain.model.CharactersRequestModel
import com.marvel.domain.usecase.repository.CharactersRepository
import javax.inject.Inject


class GetCharactersUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository
) : GetCharactersUseCase {

    override suspend fun getCharacters(request: CharactersRequestModel) =
        repository.getCharacters(request)

}

/*class GetCharacterListUseCaseImpl @Inject constructor(private val characterListRepository: CharacterListRepository) :
    GetCharacterListUseCase {
    override suspend fun invoke(): Result<List<MarvelCharacter>> =
        characterListRepository.getCharacters()
}
*/
