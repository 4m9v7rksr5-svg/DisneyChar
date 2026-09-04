package com.edcode.disneychar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.edcode.disneychar.R
import com.edcode.disneychar.domain.DisneyCharacter
import com.edcode.disneychar.ui.theme.DisneyCharTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds


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

            viewModel.setScreenTittle("Disney Characters")
            DisneyCharTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (showTopBar) {
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
                    },
                    floatingActionButton = {
                        if (currentRoute == "disneyChar") {
                            FloatingActionButton(
                                onClick = { viewModel.getFavorite() },
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
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationRoute(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: DisneyViewModel = hiltViewModel()
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
        composable("disneyDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DisneyDetailScreen(modifier = modifier,navController = navController,id=id, viewModel = viewModel)
        }
    }
}

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(durationMillis = 1000)
        )
        delay(1500.milliseconds)
        onAnimationFinished()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CircularProgressIndicator()
    }
}

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

    character?.let{
        CharacterItemDetails(character = it,modifier)
    }?: Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

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

data class CharacterDetailInfo(val title: String, val items: List<String>)

@Composable
fun CharacterItemDetails(character: DisneyCharacter, modifier: Modifier) {
    val detailsList = listOf(
        CharacterDetailInfo("Films", character.films),
        CharacterDetailInfo("TV Shows", character.tvShows),
        CharacterDetailInfo("Video Games", character.videoGames),
        CharacterDetailInfo("Short Films", character.shortFilms),
        CharacterDetailInfo("Park Attractions", character.parkAttractions),
        CharacterDetailInfo("Allies", character.allies),
        CharacterDetailInfo("Enemies", character.enemies)
    ).filter { it.items.isNotEmpty() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder),
            model = character.imageUrl,
            contentDescription = character.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            detailsList.forEach { detail ->
                DetailSection(title = detail.title, items = detail.items)
            }
        }
    }
}

@Composable
fun DetailSection(title: String, items: List<String>) {
    if (items.isNotEmpty()) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = items.joinToString(", "),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun CharacterItem(character: DisneyCharacter, onStarClick: (Int) -> Unit, route: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                println(character)
                route.invoke()
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder),
                model = character.imageUrl,
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 8.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge
                )

                if (character.films.isNotEmpty()) {
                    Text(
                        text = "Films: ${character.films.take(1).joinToString(",")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                } else if (character.tvShows.isNotEmpty()) {
                    Text(
                        text = "TV Shows: ${character.tvShows.take(1).joinToString(", ")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            IconButton(onClick = { onStarClick(character.id) }) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Default.Star else Icons.Outlined.Star,
                    contentDescription = "Favorite",
                    tint = if (character.isFavorite) Color(0xFFFFD700) else Color.Gray,
                )
            }
        }
    }
}

@Composable
fun EmptyState(
    message: String,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Image(
                painter = painterResource(R.drawable.sad_disney),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
        if (onRetry != null) {
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Reintentar")
            }
        }
    }
}