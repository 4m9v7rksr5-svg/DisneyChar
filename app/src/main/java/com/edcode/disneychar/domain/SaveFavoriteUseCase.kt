package com.edcode.disneychar.domain

import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(private val repository: DisneyRepository) {
    suspend operator fun invoke(id: Int) {
        repository.toggleFavorite(id)
    }
}