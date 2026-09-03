package com.edcode.disneychar.domain

import kotlinx.coroutines.flow.Flow

interface DisneyRepository {
    suspend fun getCharacters(): Flow<List<DisneyCharacter>>
}
