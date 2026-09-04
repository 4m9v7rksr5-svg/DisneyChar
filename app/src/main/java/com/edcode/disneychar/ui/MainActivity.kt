package com.edcode.disneychar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.edcode.disneychar.ui.components.CharacterList
import com.edcode.disneychar.ui.navigation.BottomNavItem
import com.edcode.disneychar.ui.navigation.NavigationRoute
import com.edcode.disneychar.ui.theme.DisneyCharTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val canNavigateBack = currentRoute != "disneyChar" && currentRoute != "splash"
            val showTopBar = currentRoute != "splash"
            val viewModel: DisneyViewModel = hiltViewModel()
            val isSearchActive by viewModel.isSearchActive.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()
            val isOnline by viewModel.isOnline.collectAsState()

            val items = if (isOnline) {
                listOf(BottomNavItem.Home, BottomNavItem.Favorites)
            } else {
                listOf(BottomNavItem.Favorites)
            }

            DisneyCharTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (showTopBar) {
                            if (isSearchActive && currentRoute == "disneyChar") {
                                SearchBar(
                                    query = searchQuery,
                                    onQueryChange = { viewModel.onSearchQueryChanged(it) },
                                    onSearch = { },
                                    active = true,
                                    onActiveChange = { if (!it) viewModel.toggleSearch() },
                                    placeholder = { Text("Search characters...") },
                                    leadingIcon = {
                                        IconButton(onClick = { viewModel.toggleSearch() }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Back"
                                            )
                                        }
                                    },
                                    trailingIcon = {
                                        if (searchQuery.isNotEmpty()) {
                                            IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Clear"
                                                )
                                            }
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    val characters by viewModel.state.collectAsState()
                                    CharacterList(
                                        characters = characters,
                                        onCharacterClick = { id ->
                                            viewModel.toggleSearch()
                                            navController.navigate("disneyDetail/$id")
                                        },
                                        onStarClick = { id -> viewModel.saveFavorite(id) }
                                    )
                                }
                            } else {
                                CenterAlignedTopAppBar(
                                    title = { Text(viewModel.currentTittle.collectAsState().value) },
                                    navigationIcon = {
                                        if (canNavigateBack) {
                                            IconButton(onClick = { navController.navigateUp() }) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                    contentDescription = "Back"
                                                )
                                            }
                                        }
                                    },
                                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                )
                            }
                        }
                    },
                    bottomBar = {
                        if (showTopBar && !isSearchActive && isOnline && (currentRoute == "disneyChar" || currentRoute == "favorites")) {
                            NavigationBar {
                                val currentDestination = navBackStackEntry?.destination
                                items.forEach { item ->
                                    val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        },
                                        label = { Text(item.title) },
                                        selected = isSelected,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },
                    floatingActionButton = {
                        if (currentRoute == "disneyChar" && !isSearchActive) {
                            FloatingActionButton(
                                onClick = { viewModel.toggleSearch() },
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavigationRoute(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


