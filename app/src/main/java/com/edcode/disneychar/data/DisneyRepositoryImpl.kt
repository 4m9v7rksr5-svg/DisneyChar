package com.edcode.disneychar.data

import com.edcode.disneychar.data.local.DisneyDao
import com.edcode.disneychar.data.local.FavoritesDataStore
import com.edcode.disneychar.data.local.toDomain
import com.edcode.disneychar.data.local.toEntity
import com.edcode.disneychar.data.network.ConnectivityManager
import com.edcode.disneychar.data.remote.DisneyChar
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.domain.DisneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.edcode.disneychar.data.remote.toDomain as toDomainDto

class DisneyRepositoryImpl @Inject constructor(
    private val api: DisneyChar,
    private val dao: DisneyDao,
    private val favoritesDataStore: FavoritesDataStore,
    private val connectivityManager: ConnectivityManager
) : DisneyRepository {
    override suspend fun getCharacters(query: String): Flow<List<DisneyCharacter>> {
        if (connectivityManager.isOnline() && query.isEmpty()) {
            try {
                val response = api.getCharacters(1, 50)
                val entities = response.data.map { it.toDomainDto().toEntity() }
                dao.insertCharacters(entities)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val charactersFlow = if (query.isEmpty()) {
            dao.getAllCharacters()
        } else {
            dao.searchCharacters(query)
        }

        return combine(
            charactersFlow,
            favoritesDataStore.favoriteIds
        ) { entities, favoriteIds ->
            val domainList = entities.map { entity ->
                entity.toDomain().copy(
                    isFavorite = favoriteIds.contains(entity.id.toString())
                )
            }
            if (connectivityManager.isOnline()) {
                domainList
            } else {
                domainList.filter { it.isFavorite }
            }
        }
    }

    override suspend fun getCharacter(id: Int): Flow<DisneyCharacter> {
        return dao.getCharacterById(id).map { entity ->
            entity.toDomain()
        }
    }


    override suspend fun toggleFavorite(id: Int) {
        favoritesDataStore.toggleFavorite(id)
    }

}

