package com.edcode.disneychar.domain

data class DisneyCharacter(
    val id: Int,
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
