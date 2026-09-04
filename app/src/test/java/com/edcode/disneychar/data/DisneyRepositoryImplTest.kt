package com.edcode.disneychar.data

import com.edcode.disneychar.data.local.DisneyDao
import com.edcode.disneychar.data.local.DisneyCharacterEntity
import com.edcode.disneychar.data.local.FavoritesDataStore
import com.edcode.disneychar.data.network.ConnectivityManager
import com.edcode.disneychar.data.remote.DisneyChar
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class DisneyRepositoryImplTest {

    @Mock
    lateinit var api: DisneyChar
    @Mock
    lateinit var dao: DisneyDao
    @Mock
    lateinit var favoritesDataStore: FavoritesDataStore
    @Mock
    lateinit var connectivityManager: ConnectivityManager

    private lateinit var repository: DisneyRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = DisneyRepositoryImpl(api, dao, favoritesDataStore, connectivityManager)
    }

    @Test
    fun `getCharacters when offline should filter only favorites`() = runTest {
        val character1 = DisneyCharacterEntity(1, "Mickey", null, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), "url")
        val character2 = DisneyCharacterEntity(2, "Donald", null, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), "url")
        
        whenever(connectivityManager.isOnline()).thenReturn(false)
        whenever(dao.getAllCharacters()).thenReturn(flowOf(listOf(character1, character2)))
        whenever(favoritesDataStore.favoriteIds).thenReturn(flowOf(setOf("1"))) // Only Mickey is favorite

        val result = repository.getCharacters("").first()

        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo(1)
        assertThat(result[0].name).isEqualTo("Mickey")
        assertThat(result[0].isFavorite).isTrue()
    }
}
