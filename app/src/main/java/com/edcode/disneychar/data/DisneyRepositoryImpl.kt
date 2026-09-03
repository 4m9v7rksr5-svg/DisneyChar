package com.edcode.disneychar.data

import com.edcode.disneychar.data.local.DisneyDao
import com.edcode.disneychar.data.local.toDomain
import com.edcode.disneychar.data.local.toEntity
import com.edcode.disneychar.data.remote.DisneyChar
import com.edcode.disneychar.data.remote.toDomain as toDomainDto
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.domain.DisneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DisneyRepositoryImpl @Inject constructor(
    private val api: DisneyChar,
    private val dao: DisneyDao
) : DisneyRepository {
    override suspend fun getCharacters(): Flow<List<DisneyCharacter>> {
        try {
            val response = api.getCharacters(1, 50)
            val entities = response.data.map { it.toDomainDto().toEntity() }
            dao.insertCharacters(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dao.getAllCharacters().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
