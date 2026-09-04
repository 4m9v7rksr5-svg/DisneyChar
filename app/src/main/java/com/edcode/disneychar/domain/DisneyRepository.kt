package com.edcode.disneychar.domain

import kotlinx.coroutines.flow.Flow

interface DisneyRepository {
    suspend fun getCharacters(query: String = ""): Flow<List<DisneyCharacter>>
    suspend fun getCharacter(id:Int): Flow<DisneyCharacter>
    suspend fun toggleFavorite(id: Int)
}
