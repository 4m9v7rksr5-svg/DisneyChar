package com.edcode.disneychar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edcode.disneychar.data.network.ConnectivityManager
import com.edcode.disneychar.domain.DisneyCharSingleUseCase
import com.edcode.disneychar.domain.DisneyCharUseCase
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.domain.GetFavoritesUseCase
import com.edcode.disneychar.domain.SaveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class DisneyViewModel @Inject constructor(
    private val getCharacterUseCase: DisneyCharUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getSingleCharacterUseCase: DisneyCharSingleUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase, // Re-added
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    private val _state = MutableStateFlow<List<DisneyCharacter>>(emptyList())
    val state: StateFlow<List<DisneyCharacter>> = _state

    private val _favoritesState = MutableStateFlow<List<DisneyCharacter>>(emptyList())
    val favoritesState: StateFlow<List<DisneyCharacter>> = _favoritesState

    private val _characterState = MutableStateFlow<DisneyCharacter?>(null)
    val characterState: StateFlow<DisneyCharacter?> = _characterState

    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline

    private val _currentTitle = MutableStateFlow("Disney Characters")
    val currentTitle: StateFlow<String> = _currentTitle

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive = _isSearchActive.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        observeSearch()
        getCharacters()
        getFavorites()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleSearch() {
        _isSearchActive.value = !_isSearchActive.value
        if (!_isSearchActive.value) {
            _searchQuery.value = ""
        }
    }

    private fun observeSearch() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300.milliseconds)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    _isOnline.emit(connectivityManager.isOnline())
                    getCharacterUseCase(query)
                }
                .collectLatest { characters ->
                    _state.emit(characters)
                }
        }
    }

    fun setScreenTitle(title: String) {
        viewModelScope.launch {
            _currentTitle.emit(title)
        }
    }

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            getSingleCharacterUseCase(id).collect {
                _characterState.emit(it)
            }
        }
    }

    fun saveFavorite(id: Int) {
        viewModelScope.launch {
            saveFavoriteUseCase(id)
        }
    }

    fun refreshCharacters() {
        getCharacters(forceRefresh = true)
    }

    private fun getCharacters(forceRefresh: Boolean = false) {
        if (_state.value.isEmpty() || forceRefresh) {
            viewModelScope.launch {
                if (forceRefresh) _isRefreshing.emit(true)
                _isOnline.emit(connectivityManager.isOnline())
                getCharacterUseCase().collectLatest { characters ->
                    _state.emit(characters)
                    _isRefreshing.emit(false)
                }
            }
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collectLatest { favorites ->
                _favoritesState.emit(favorites)
            }
        }
    }
}
