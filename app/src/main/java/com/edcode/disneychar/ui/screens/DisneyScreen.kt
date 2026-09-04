package com.edcode.disneychar.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisneyScreen(
    modifier: Modifier = Modifier,
    viewModel: DisneyViewModel,
    navController: NavHostController,
) {
    val characters by viewModel.state.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(isOnline) {
        if (isOnline) {
            viewModel.setScreenTittle("Personajes de Disney")
        } else {
            viewModel.setScreenTittle("Favoritos")
        }
    }

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refreshCharacters() }
    ) {
        if (characters.isEmpty()) {
            if (isOnline) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                EmptyState(
                    message = "Agrega a tus personajes favoritos",
                    onRetry = { viewModel.refreshCharacters() }
                )
            }
        } else {
            CharacterList(
                characters = characters,
                onCharacterClick = { id -> navController.navigate("disneyDetail/$id") },
                onStarClick = { id -> viewModel.saveFavorite(id) }
            )
        }
    }
}
