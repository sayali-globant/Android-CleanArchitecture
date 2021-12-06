package com.marvel.data.source.characterlist

import com.marvel.data.characters.api.CharactersApiService
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.mapper.charcters.CharactersResponseMapper
import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * This class is CharacterListDataSource implementation
 */
class CharactersDataSourceImpl @Inject constructor(
    private val service: CharactersApiService,
    private val mapper: CharactersResponseMapper
) : CharactersDataSource {
    override suspend fun getCharacters(charactersRequest: CharactersRequest): ApiState<List<CharacterModel>> =
        withContext(
            Dispatchers.IO
        ) {
            try {
                val response = service.getMarvelCharacters(
                    charactersRequest.ts,
                    charactersRequest.apiKey,
                    charactersRequest.hashKey
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@withContext ApiState.success(
                            mapper.toCharacterList(it)
                        )
                    } ?: return@withContext ApiState.error(Exception().message ?: "", null)

                } else {
                    return@withContext ApiState.error(response.message(), null)
                }
            } catch (e: Exception) {
                return@withContext ApiState.error(e.message ?: "", null)
            }
        }
}
