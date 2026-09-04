package com.edcode.disneychar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edcode.disneychar.ConnectivityManager
import com.edcode.disneychar.domain.DisneyCharSingleUseCase
import com.edcode.disneychar.domain.DisneyCharUseCase
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.domain.GetFavoritesUseCase
import com.edcode.disneychar.domain.SaveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyViewModel @Inject constructor(
    private val getCharacterUseCase: DisneyCharUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getSingleCharacterUseCase: DisneyCharSingleUseCase,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    private val _state = MutableStateFlow<List<DisneyCharacter>>(emptyList())
    val state: StateFlow<List<DisneyCharacter>> = _state

    private val _characterState = MutableStateFlow<DisneyCharacter?>(null)
    val characterState: StateFlow<DisneyCharacter?> = _characterState

    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline

    private val _currentTittle = MutableStateFlow("")
    val currentTittle: StateFlow<String> = _currentTittle

    init {
        getCharacters()
    }

    fun setScreenTittle(tittle: String) {
        viewModelScope.launch {
            _currentTittle.emit(tittle)
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

    private fun getCharacters() {
        viewModelScope.launch {
            _isOnline.emit(connectivityManager.isOnline())

            if (_isOnline.value) {
                getCharacterUseCase().collect { _state.emit(it) }
            } else {
                getFavoritesUseCase().collect { _state.emit(it) }
            }
        }
    }

}