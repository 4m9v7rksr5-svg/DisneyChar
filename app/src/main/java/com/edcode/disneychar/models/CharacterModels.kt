package com.edcode.disneychar.models

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("data") val data: List<DisneyCharacter>,
    @SerializedName("info") val info: Info
)

data class Info(
    @SerializedName("count") val count: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("previousPage") val previousPage: String?,
    @SerializedName("nextPage") val nextPage: String?
)

data class DisneyCharacter(
    @SerializedName("_id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("films") val films: List<String>,
    @SerializedName("tvShows") val tvshows: List<String>,
    @SerializedName("sourceUrl") val sourceUrl: String?
)
