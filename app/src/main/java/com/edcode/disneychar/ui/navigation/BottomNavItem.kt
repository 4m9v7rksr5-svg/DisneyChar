package com.edcode.disneychar.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem("disneyChar", "Home",
        Icons.Filled.Home,
        Icons.Outlined.Home)
    object Favorites : BottomNavItem("favorites", "Favorites",
        Icons.Outlined.Star,
        Icons.Default.StarBorder)
}
