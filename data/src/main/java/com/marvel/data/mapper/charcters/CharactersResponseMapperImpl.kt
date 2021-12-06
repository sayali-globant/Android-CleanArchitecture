package com.marvel.data.mapper.charcters

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail


class CharactersResponseMapperImpl : CharactersResponseMapper {
    override fun toCharacterList(response: MarvelCharacterResponse): List<CharacterModel> =
        response.mainData?.results?.map {
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
