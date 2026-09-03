package com.edcode.disneychar.models

import com.edcode.disneychar.data.remote.DisneyCharacterDto
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("data") val data: List<DisneyCharacterDto>,
    @SerializedName("info") val info: Info
)

data class Info(
    @SerializedName("count") val count: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("previousPage") val previousPage: String?,
    @SerializedName("nextPage") val nextPage: String?
)


