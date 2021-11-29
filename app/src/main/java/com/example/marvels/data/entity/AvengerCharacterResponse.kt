package com.example.marvels.data.entity


import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AvengerCharacterResponse(
    @JsonProperty("attributionHTML")
    val attributionHTML: String? = null,
    @JsonProperty("attributionText")
    val attributionText: String? = null,
    @JsonProperty("code")
    val code: Int? = null,
    @JsonProperty("copyright")
    val copyright: String? = null,
    @JsonProperty("data")
    val mainData: Data? = null,
    @JsonProperty("etag")
    val etag: String? = null,
    @JsonProperty("status")
    val status: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class Comics(
    @JsonProperty("available")
    val available: Int? = null,
    @JsonProperty("collectionURI")
    val collectionURI: String? = null,
    @JsonProperty("items")
    val items: List<Item>? = null,
    @JsonProperty("returned")
    val returned: Int? = null
): Parcelable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Data(
    @JsonProperty("count")
    val count: Int? = null,
    @JsonProperty("limit")
    val limit: Int? = null,
    @JsonProperty("offset")
    val offset: Int? = null,
    @JsonProperty("results")
    val results: List<CharacterDetail>? = null,
    @JsonProperty("total")
    val total: Int? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class Events(
    @JsonProperty("available")
    val available: Int? = null,
    @JsonProperty("collectionURI")
    val collectionURI: String? = null,
    @JsonProperty("items")
    val items: List<Item>? = null,
    @JsonProperty("returned")
    val returned: Int? = null
): Parcelable

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Item(
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("resourceURI")
    val resourceURI: String? = null
): Parcelable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class CharacterDetail(
    @JsonProperty("comics")
    val comics: Comics? = null,
    @JsonProperty("description")
    val description: String? = null,
    @JsonProperty("events")
    val events: Events? = null,
    @JsonProperty("id")
    val id: Int? = null,
    @JsonProperty("modified")
    val modified: String? = null,
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("resourceURI")
    val resourceURI: String? = null,
    @JsonProperty("series")
    val series: Series? = null,
    @JsonProperty("stories")
    val stories: Stories? = null,
    @JsonProperty("thumbnail")
    val thumbnail: Thumbnail? = null,
    @JsonProperty("urls")
    val urls: List<Url>? = null
) : Parcelable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class Series(
    @JsonProperty("available")
    val available: Int? = null,
    @JsonProperty("collectionURI")
    val collectionURI: String? = null,
    @JsonProperty("items")
    val items: List<Item>? = null,
    @JsonProperty("returned")
    val returned: Int? = null
) : Parcelable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class Stories(
    @JsonProperty("available")
    val available: Int? = null,
    @JsonProperty("collectionURI")
    val collectionURI: String? = null,
    @JsonProperty("items")
    val items: List<Item>? = null,
    @JsonProperty("returned")
    val returned: Int? = null
) : Parcelable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class Thumbnail(
    @JsonProperty("extension")
    val extension: String? = null,
    @JsonProperty("path")
    val path: String? = null
) : Parcelable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Parcelize
data class Url(
    @JsonProperty("type")
    val type: String? = null,
    @JsonProperty("url")
    val url: String? = null
) : Parcelable