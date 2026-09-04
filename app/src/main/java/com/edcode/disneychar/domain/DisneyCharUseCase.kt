package com.edcode.disneychar.domain

import javax.inject.Inject

class DisneyCharUseCase @Inject constructor(private val repository: DisneyRepository) {

    suspend operator fun invoke(query: String = "") =
        repository.getCharacters(query)
    }


