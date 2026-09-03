package com.edcode.disneychar.data

import com.edcode.disneychar.data.local.DisneyDao
import com.edcode.disneychar.data.local.FavoritesDataStore
import com.edcode.disneychar.data.local.toDomain
import com.edcode.disneychar.data.local.toEntity
import com.edcode.disneychar.data.remote.DisneyChar
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.domain.DisneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import com.edcode.disneychar.data.remote.toDomain as toDomainDto

class DisneyRepositoryImpl @Inject constructor(
    private val api: DisneyChar,
    private val dao: DisneyDao,
    private val favoritesDataStore: FavoritesDataStore
) : DisneyRepository {
    override suspend fun getCharacters(): Flow<List<DisneyCharacter>> {
        try {
            val response = api.getCharacters(1, 50)
            val entities = response.data.map { it.toDomainDto().toEntity() }
            dao.insertCharacters(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }

            return combine(
                dao.getAllCharacters(),
                favoritesDataStore.favoriteIds
            ) { entities, favoriteIds ->
                entities.map { entity ->
                    entity.toDomain().copy(
                        isFavorite = favoriteIds.contains(entity.id.toString())
                    )
                }
            }
        }

    override suspend fun toggleFavorite(id: Int) {
        favoritesDataStore.toggleFavorite(id) // Llama al DataStore
    }

}

