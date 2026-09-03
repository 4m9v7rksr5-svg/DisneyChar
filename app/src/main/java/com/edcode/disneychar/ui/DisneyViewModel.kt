package com.edcode.disneychar.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _state = mutableStateOf<List<DisneyCharacter>>(emptyList())
    val state: State<List<DisneyCharacter>> = _state

    init {
        getCharacters()
    }

    fun saveFavorite(id:Int) {

    }


    private fun getCharacters() {
        viewModelScope.launch {
            try {
                getCharacterUseCase.invoke().collect { characters ->
                    _state.value = characters
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}