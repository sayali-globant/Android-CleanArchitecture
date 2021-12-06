package com.marvel.data.mapper.characterdetails

import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.domain.model.CharacterModel


interface CharacterDetailsResponseMapper {
    fun toCharacterDetails(response: MarvelCharacterResponse): CharacterModel
}
