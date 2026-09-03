package com.edcode.disneychar.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edcode.disneychar.domain.DisneyCharUseCase
import com.edcode.disneychar.domain.DisneyCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyViewModel @Inject constructor(
    private val getCharacterUseCase: DisneyCharUseCase
) : ViewModel() {

    private val _state = mutableStateOf<List<DisneyCharacter>>(emptyList())
    val state: State<List<DisneyCharacter>> = _state

    init {
        getCharacters()
    }

    private fun setFavorites()
    {
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                val response = getCharacterUseCase.invoke()
                _state.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}