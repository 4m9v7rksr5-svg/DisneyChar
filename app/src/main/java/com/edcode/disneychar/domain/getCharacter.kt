package com.edcode.disneychar.domain

import javax.inject.Inject

class DisneyCharUseCase @Inject constructor(private val repository: DisneyRepository) {

    suspend operator fun invoke() =
        repository.getCharacters()
    }

class SaveFavoriteUseCase @Inject constructor(private val repository: DisneyRepository) {

}

class GetFavoritesUseCase @Inject constructor(private val repository: DisneyRepository) {}

