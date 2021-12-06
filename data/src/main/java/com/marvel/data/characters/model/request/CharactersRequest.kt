package com.marvel.data.characters.model.request

data class CharactersRequest(
    val ts: Long = 0,
    val apiKey: String = "",
    val hashKey: String = "",
    var id: String = ""
)


