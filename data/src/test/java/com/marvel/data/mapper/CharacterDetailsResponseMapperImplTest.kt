package com.marvel.data.mapper

import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.Data
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.Thumbnail
import com.marvel.data.mapper.characterdetails.CharacterDetailsResponseMapper
import com.marvel.data.mapper.characterdetails.CharacterDetailsResponseMapperImpl
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharacterDetailsResponseMapperImplTest {
    private lateinit var mCharacterListResponse: MarvelCharacterResponse
    private lateinit var mCharacterDetailsResponseMapper: CharacterDetailsResponseMapper
    private lateinit var mMarvelCharacter: CharacterModel

    @Before
    fun setUp() {
        mCharacterListResponse = MarvelCharacterResponse(
            mainData = Data(
                listOf(
                    CharacterDetail(
                        id = 2,
                        name = "Captain America",
                        description = "Steve Rogers",
                        thumbnail = Thumbnail(path = "captain_america", extension = "jpg")
                    )
                )
            )
        )
        mCharacterDetailsResponseMapper = CharacterDetailsResponseMapperImpl()
        mMarvelCharacter =
            CharacterModel(
                id = 2,
                name = "Captain America",
                description = "Steve Rogers",
                thumbnail = CharacterThumbnail("captain_america", "jpg")
            )
    }

    @Test
    fun toCharacterDetailsTest() {
        val result = mCharacterDetailsResponseMapper.toCharacterDetails(mCharacterListResponse)
        Assert.assertEquals(result, mMarvelCharacter)
    }
}
