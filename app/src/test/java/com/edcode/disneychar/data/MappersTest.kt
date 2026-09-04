package com.edcode.disneychar.data

import com.edcode.disneychar.data.local.DisneyCharacterEntity
import com.edcode.disneychar.data.local.toDomain
import com.edcode.disneychar.data.remote.DisneyCharacterDto
import com.edcode.disneychar.data.remote.toDomain
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MappersTest {

    @Test
    fun `dto toDomain should map all fields correctly`() {
        val dto = DisneyCharacterDto(
            id = 1,
            name = "Mickey Mouse",
            imageUrl = "url",
            films = listOf("Film 1"),
            tvShows = listOf("Show 1"),
            shortFilms = emptyList(),
            parkAttractions = emptyList(),
            videoGames = emptyList(),
            allies = emptyList(),
            enemies = emptyList(),
            sourceUrl = "source"
        )

        val domain = dto.toDomain()

        assertThat(domain.id).isEqualTo(dto.id)
        assertThat(domain.name).isEqualTo(dto.name)
        assertThat(domain.imageUrl).isEqualTo(dto.imageUrl)
        assertThat(domain.films).isEqualTo(dto.films)
        assertThat(domain.isFavorite).isFalse()
    }

    @Test
    fun `entity toDomain should map all fields correctly`() {
        val entity = DisneyCharacterEntity(
            id = 1,
            name = "Mickey Mouse",
            imageUrl = "url",
            films = listOf("Film 1"),
            tvShows = listOf("Show 1"),
            shortFilms = emptyList(),
            parkAttractions = emptyList(),
            videoGames = emptyList(),
            allies = emptyList(),
            enemies = emptyList(),
            sourceUrl = "source"
        )

        val domain = entity.toDomain()

        assertThat(domain.id).isEqualTo(entity.id)
        assertThat(domain.name).isEqualTo(entity.name)
        assertThat(domain.isFavorite).isFalse()
    }
}
