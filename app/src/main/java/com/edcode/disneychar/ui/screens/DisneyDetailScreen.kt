package com.edcode.disneychar.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.edcode.disneychar.ui.DisneyViewModel
import com.edcode.disneychar.ui.components.CharacterItemDetails

@Composable
fun DisneyDetailScreen(
    navController: NavHostController,
    id: Int,
    viewModel: DisneyViewModel,
    modifier: Modifier
) {
    BackHandler {
        navController.popBackStack()
    }

    val character by viewModel.characterState.collectAsState()

    LaunchedEffect(id) {
        viewModel.getCharacter(id)
    }

    LaunchedEffect(character) {
        viewModel.setScreenTittle(" ${character?.name ?: "Detail Screen"}")
    }

    character?.let {
        CharacterItemDetails(character = it, modifier)
    } ?: Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
