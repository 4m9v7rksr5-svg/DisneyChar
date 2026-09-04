package com.edcode.disneychar.ui

import com.edcode.disneychar.MainDispatcherRule
import com.edcode.disneychar.data.network.ConnectivityManager
import com.edcode.disneychar.domain.DisneyCharSingleUseCase
import com.edcode.disneychar.domain.DisneyCharUseCase
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.domain.GetFavoritesUseCase
import com.edcode.disneychar.domain.SaveFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DisneyViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var getCharacterUseCase: DisneyCharUseCase

    @Mock
    lateinit var saveFavoriteUseCase: SaveFavoriteUseCase

    @Mock
    lateinit var getSingleCharacterUseCase: DisneyCharSingleUseCase

    @Mock
    lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    private lateinit var viewModel: DisneyViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `init should call getCharacters and update state`() = runTest {
        val characters = listOf(
            DisneyCharacter(1, "Mickey", null, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), "url", false)
        )
        
        whenever(connectivityManager.isOnline()).thenReturn(true)
        whenever(getCharacterUseCase(any())).thenReturn(flowOf(characters))
        whenever(getFavoritesUseCase()).thenReturn(flowOf(emptyList()))

        viewModel = DisneyViewModel(
            getCharacterUseCase,
            saveFavoriteUseCase,
            getSingleCharacterUseCase,
            getFavoritesUseCase,
            connectivityManager
        )

        assertThat(viewModel.state.value).isEqualTo(characters)
    }

    @Test
    fun `when offline should show only favorites`() = runTest {
        val favorites = listOf(
            DisneyCharacter(1, "Mickey", null, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), "url", true)
        )
        
        whenever(connectivityManager.isOnline()).thenReturn(false)
        whenever(getCharacterUseCase(any())).thenReturn(flowOf(favorites))
        whenever(getFavoritesUseCase()).thenReturn(flowOf(favorites))

        viewModel = DisneyViewModel(
            getCharacterUseCase,
            saveFavoriteUseCase,
            getSingleCharacterUseCase,
            getFavoritesUseCase,
            connectivityManager
        )

        assertThat(viewModel.state.value).isEqualTo(favorites)
        assertThat(viewModel.isOnline.value).isFalse()
    }
}
