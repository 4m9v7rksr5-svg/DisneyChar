package com.edcode.disneychar.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.edcode.disneychar.domain.DisneyCharacter

@Composable
fun CharacterList(
    characters: List<DisneyCharacter>,
    onCharacterClick: (Int) -> Unit,
    onStarClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(characters) { character ->
            CharacterItem(
                character = character,
                onStarClick = { onStarClick(character.id) },
                route = { onCharacterClick(character.id) }
            )
        }
    }
}
