package com.edcode.disneychar.domain

import javax.inject.Inject

class DisneyCharUseCase @Inject constructor(private val repository: DisneyRepository) {

    operator suspend fun invoke() =
        repository.getCharacters()
    }

