package com.edcode.disneychar.data

import com.edcode.disneychar.data.remote.toDomain
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.domain.DisneyRepository
import com.edcode.disneychar.models.DisneyChar
import javax.inject.Inject

class DisneyRepositoryImpl @Inject constructor(
    private val api: DisneyChar
) : DisneyRepository {
    override suspend fun getCharacters(): List<DisneyCharacter> {
        return api.getCharacters(1, 50)
            .data.map{ it.toDomain() }
            .sortedBy { it.name }
    }
}
