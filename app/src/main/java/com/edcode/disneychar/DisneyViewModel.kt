package com.edcode.disneychar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edcode.disneychar.models.DisneyChar
import com.edcode.disneychar.models.DisneyCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisneyViewModel @Inject constructor(
    private val disneyChar: DisneyChar
) : ViewModel() {

    private val _state = mutableStateOf<List<DisneyCharacter>>(emptyList())
    val state: State<List<DisneyCharacter>> = _state

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                val response = disneyChar.getCharacters()
                _state.value = response.data
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}
