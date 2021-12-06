package com.marvel.data.mapper.charcters

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.domain.model.CharacterModel


interface CharactersResponseMapper {
    fun toCharacterList(response: MarvelCharacterResponse): List<CharacterModel>
}
