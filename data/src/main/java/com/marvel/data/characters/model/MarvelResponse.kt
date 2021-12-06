package com.marvel.data.characters.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MarvelCharacterResponse(
    @JsonProperty("data")
    val mainData: Data? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Data(
    @JsonProperty("results")
    val results: List<CharacterDetail>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CharacterDetail(
    @JsonProperty("description")
    val description: String? = null,
    @JsonProperty("id")
    val id: Int? = null,
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("thumbnail")
    val thumbnail: Thumbnail? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Thumbnail(
    @JsonProperty("extension")
    val extension: String? = null,
    @JsonProperty("path")
    val path: String? = null
)

