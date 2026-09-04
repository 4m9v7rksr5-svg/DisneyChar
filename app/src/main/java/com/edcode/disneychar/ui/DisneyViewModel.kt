package com.edcode.disneychar.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _state = mutableStateOf<List<DisneyCharacter>>(emptyList())
    val state: State<List<DisneyCharacter>> = _state

    private val _characterState = mutableStateOf<DisneyCharacter?>(null)
    val characterState: State<DisneyCharacter?> = _characterState

    private val _isOnline = mutableStateOf(true)
    val isOnline: State<Boolean> = _isOnline

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
                _characterState.value = it
            }
        }
    }

    fun saveFavorite(id: Int) {
        viewModelScope.launch {
            saveFavoriteUseCase(id)
        }
    }

    fun getFavorite() {
        viewModelScope.launch {
            getFavoritesUseCase().collect {
                println("Data  $it")
             //   _state.value = it
            }
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _isOnline.value = connectivityManager.isOnline()

            if (_isOnline.value) {
                getCharacterUseCase().collect { _state.value = it }
            } else {
                getFavoritesUseCase().collect { _state.value = it }
            }
        }
    }

}