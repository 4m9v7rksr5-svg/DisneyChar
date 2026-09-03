package com.edcode.disneychar.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: DisneyRepository) {
    suspend operator fun invoke(): Flow<List<DisneyCharacter>> {
        return repository.getCharacters().map { list ->
            list.filter { it.isFavorite }
        }
    }
}