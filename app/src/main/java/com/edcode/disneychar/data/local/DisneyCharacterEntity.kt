package com.edcode.disneychar.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edcode.disneychar.domain.DisneyCharacter

@Entity(tableName = "characters")
data class DisneyCharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?,
    val films: List<String>,
    val tvShows: List<String>,
    val shortFilms: List<String>,
    val parkAttractions: List<String>,
    val videoGames: List<String>,
    val allies: List<String>,
    val enemies: List<String>,
    val sourceUrl: String?
)

fun DisneyCharacterEntity.toDomain() = DisneyCharacter(
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
    sourceUrl = sourceUrl
)

fun DisneyCharacter.toEntity() = DisneyCharacterEntity(
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
    sourceUrl = sourceUrl
)
