package com.marvel.data.mapper

import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.Data
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.Thumbnail
import com.marvel.data.mapper.charcters.CharactersResponseMapper
import com.marvel.data.mapper.charcters.CharactersResponseMapperImpl
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharacterThumbnail
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class CharactersResponseMapperImplTest {
    private lateinit var characterListResponse: MarvelCharacterResponse
    private lateinit var characterListResponseMapper: CharactersResponseMapper
    private lateinit var list: List<CharacterModel>

    @Before
    fun setUp() {
        characterListResponse = MarvelCharacterResponse(
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
        characterListResponseMapper = CharactersResponseMapperImpl()
        list = listOf(
            CharacterModel(
                id = 2,
                name = "Captain America",
                description = "Steve Rogers",
                thumbnail = CharacterThumbnail("captain_america", "jpg")
            )
        )
    }

    @Test
    fun toCharacterListTest() {
        val result = characterListResponseMapper.toCharacterList(characterListResponse)
        Assert.assertEquals(result, list)
        Assert.assertTrue(result.containsAll(list))
    }
}
