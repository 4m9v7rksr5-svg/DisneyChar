package com.edcode.disneychar.data.remote

import com.edcode.disneychar.domain.DisneyCharacter
import com.google.gson.annotations.SerializedName

data class DisneyCharacterDto(
    @SerializedName("_id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("films") val films: List<String>,
    @SerializedName("tvShows") val tvShows: List<String>,
    @SerializedName("shortFilms") val shortFilms: List<String>,
    @SerializedName("parkAttractions") val parkAttractions: List<String>,
    @SerializedName("videoGames") val videoGames: List<String>,
    @SerializedName("allies") val allies: List<String>,
    @SerializedName("enemies") val enemies: List<String>,
    @SerializedName("sourceUrl") val sourceUrl: String?
)

fun DisneyCharacterDto.toDomain() = DisneyCharacter(
    id = id,
    name = name,
    imageUrl = imageUrl,
    films = films,
    tvShows = tvShows,
    shortFilms = shortFilms,
    parkAttractions = parkAttractions,
    videoGames = videoGames,
    allies = allies,
    enemies = enemies,
    sourceUrl = sourceUrl,
    isFavorite = false
)
