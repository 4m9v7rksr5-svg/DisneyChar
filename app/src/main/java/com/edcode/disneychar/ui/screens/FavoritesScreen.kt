package com.edcode.disneychar.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.edcode.disneychar.ui.DisneyViewModel
import com.edcode.disneychar.ui.components.CharacterList
import com.edcode.disneychar.ui.components.EmptyState

@Composable
fun FavoritesScreen(
    modifier: Modifier,
    viewModel: DisneyViewModel,
    navController: NavHostController
) {
    val favorites by viewModel.favoritesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setScreenTitle("Favoritos")
    }

    if (favorites.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyState(
                message = "Aún no tienes personajes favoritos"
            )
        }
    } else {
        CharacterList(
            modifier = modifier,
            characters = favorites,
            onCharacterClick = { id -> navController.navigate("disneyDetail/$id") },
            onStarClick = { id -> viewModel.saveFavorite(id) }
        )
    }
}
