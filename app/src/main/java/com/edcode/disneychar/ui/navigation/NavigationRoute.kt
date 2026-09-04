package com.edcode.disneychar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.edcode.disneychar.ui.DisneyViewModel
import com.edcode.disneychar.ui.screens.DisneyDetailScreen
import com.edcode.disneychar.ui.screens.DisneyScreen
import com.edcode.disneychar.ui.screens.FavoritesScreen
import com.edcode.disneychar.ui.screens.SplashScreen

@Composable
fun NavigationRoute(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DisneyViewModel
) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onAnimationFinished = {
                navController.navigate("disneyChar") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("disneyChar") {
            DisneyScreen(modifier = modifier, viewModel = viewModel, navController = navController)
        }
        composable("favorites") {
            FavoritesScreen(modifier = modifier, viewModel = viewModel, navController = navController)
        }
        composable("disneyDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DisneyDetailScreen(
                modifier = modifier,
                navController = navController,
                id = id,
                viewModel = viewModel
            )
        }
    }
}
