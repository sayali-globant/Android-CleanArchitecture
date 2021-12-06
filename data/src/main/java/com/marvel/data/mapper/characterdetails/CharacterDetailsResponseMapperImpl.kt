package com.marvel.data.mapper.characterdetails

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail


/**
 * This class is for CharacterDetailsResponseMapper implementation
 */
class CharacterDetailsResponseMapperImpl : CharacterDetailsResponseMapper {
    override fun toCharacterDetails(response: MarvelCharacterResponse): CharacterModel =
        response.mainData?.results?.get(0)?.let {
            CharacterModel(
                id = it.id,
                name = it.name,
                description = it.description,
                thumbnail = CharacterThumbnail(
                    path = it.thumbnail?.path ?: "",
                    extension = it.thumbnail?.extension ?: ""
                )
            )
        }!!
}
