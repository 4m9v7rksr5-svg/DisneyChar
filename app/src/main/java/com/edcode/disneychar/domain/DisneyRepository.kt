package com.edcode.disneychar.domain

interface DisneyRepository {
    suspend fun getCharacters(): List<DisneyCharacter>
}
