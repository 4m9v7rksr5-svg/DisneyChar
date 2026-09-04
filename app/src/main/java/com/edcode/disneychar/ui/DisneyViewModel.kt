package com.edcode.disneychar.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edcode.disneychar.ConnectivityManager
import com.edcode.disneychar.domain.DisneyCharUseCase
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.domain.GetFavoritesUseCase
import com.edcode.disneychar.domain.SaveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyViewModel @Inject constructor(
    private val getCharacterUseCase: DisneyCharUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    private val _state = mutableStateOf<List<DisneyCharacter>>(emptyList())
    val state: State<List<DisneyCharacter>> = _state

    init {
        getCharacters()
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
            val isOnline = connectivityManager.isOnline()

            if (isOnline) {
                getCharacterUseCase().collect { _state.value = it }
            } else {
                getFavoritesUseCase().collect { _state.value = it }
            }
        }
    }

}