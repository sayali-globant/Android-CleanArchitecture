package com.marvel.domain.model


data class CharacterModel(
    val description: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val thumbnail: CharacterThumbnail? = null
)

data class CharacterThumbnail(val path: String, val extension: String)
