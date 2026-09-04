package com.edcode.disneychar.domain

import javax.inject.Inject

class DisneyCharUseCase @Inject constructor(private val repository: DisneyRepository) {

    suspend operator fun invoke() =
        repository.getCharacters()
    }

class DisneyCharSingleUseCase @Inject constructor(private val repository: DisneyRepository) {

    suspend operator fun invoke(id: Int) =
        repository.getCharacter(id)
}
