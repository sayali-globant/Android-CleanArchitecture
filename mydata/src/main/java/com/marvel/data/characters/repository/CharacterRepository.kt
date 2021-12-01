package com.marvel.data.characters.repository

import com.marvel.data.characters.api.CharactersApiHelper
import com.marvel.data.characters.model.request.CharactersRequest
import javax.inject.Inject


class CharacterRepository @Inject constructor(private val apiHelper: CharactersApiHelper) {

    suspend fun getCharacters(request: CharactersRequest) = apiHelper.getCharacters(request)
}
