package com.marvel.data.details.repository

import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.data.details.api.CharacterDetailsApiHelper
import javax.inject.Inject


class CharacterDetailsRepository @Inject constructor(private val apiHelper: CharacterDetailsApiHelper) {
    suspend fun getCharacterDetails(charactersRequest: CharactersRequest) =
        apiHelper.getCharacterDetails(charactersRequest)
}
