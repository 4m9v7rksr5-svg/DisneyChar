package com.edcode.disneychar.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.edcode.disneychar.ui.DisneyViewModel
import com.edcode.disneychar.ui.components.CharacterItem
import com.edcode.disneychar.ui.components.EmptyState

@Composable
fun DisneyScreen(
    modifier: Modifier = Modifier,
    viewModel: DisneyViewModel,
    navController: NavHostController,
) {
    val characters by viewModel.state.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()

    if (characters.isEmpty()) {
        if (isOnline) {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            EmptyState(
                message = "Agrega a tus personajes favoritos",
            )
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(characters) { character ->
                CharacterItem(
                    character = character,
                    onStarClick = { viewModel.saveFavorite(it) },
                    route = { navController.navigate("disneyDetail/${character.id}") }
                )
            }
        }
    }
}
