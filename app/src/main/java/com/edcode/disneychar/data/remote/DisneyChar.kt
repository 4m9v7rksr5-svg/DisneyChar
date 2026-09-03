package com.edcode.disneychar.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface DisneyChar {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 50
    ): CharacterResponse
}

data class CharacterResponse(
    val data: List<DisneyCharacterDto>,
    val info: Info
)

data class Info(
    val count: Int,
    val totalPages: Int,
    val previousPage: String?,
    val nextPage: String?
)
