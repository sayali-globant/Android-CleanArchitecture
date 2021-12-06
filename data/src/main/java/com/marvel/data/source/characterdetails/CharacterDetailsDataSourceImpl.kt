package com.marvel.data.source.characterdetails

import com.marvel.data.characters.api.CharactersApiService
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.mapper.characterdetails.CharacterDetailsResponseMapper
import com.marvel.domain.ApiState
import com.marvel.domain.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CharacterDetailsDataSourceImpl @Inject constructor(
    private val service: CharactersApiService,
    private val mapper: CharacterDetailsResponseMapper
) : CharacterDetailsDataSource {
    override suspend fun getCharacterDetails(charactersRequest: CharactersRequest): ApiState<CharacterModel> =
        withContext(
            Dispatchers.IO
        ) {
            try {
                val response = service.getCharacterDetails(
                    charactersRequest.id,
                    charactersRequest.apiKey, charactersRequest.ts, charactersRequest.hashKey
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@withContext ApiState.success(mapper.toCharacterDetails(it))
                    } ?: return@withContext ApiState.error(Exception().message ?: "", null)
                } else {
                    return@withContext ApiState.error(response.message(), null)
                }
            } catch (e: Exception) {
                return@withContext ApiState.error(e.message ?: "", null)
            }
        }
}
