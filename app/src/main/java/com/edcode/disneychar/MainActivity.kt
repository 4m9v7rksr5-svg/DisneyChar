package com.edcode.disneychar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.edcode.disneychar.models.DisneyCharacter
import com.edcode.disneychar.ui.theme.DisneyCharTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: DisneyViewModel = hiltViewModel()
            DisneyCharTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("Disney Characters") },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                ) { innerPadding ->
                    DisneyScreen(modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel)
                   }
            }
        }
    }
}

@Composable
fun DisneyScreen(
    modifier: Modifier = Modifier,
    viewModel: DisneyViewModel,
) {
    // Obtenemos el estado del ViewModel (lista de personajes)
    val characters by viewModel.state

    if (characters.isEmpty()) {
        // Pantalla de carga mientras se obtienen los datos
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // Lista deslizable de personajes
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(characters) { character ->
                CharacterItem(character = character)
            }
        }
    }
}

@Composable
fun CharacterItem(character: DisneyCharacter) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth().clickable{
                println(character)
            }
        ,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // AsyncImage de Coil gestiona la carga de la URL automáticamente
            AsyncImage(
                model = character.imageUrl,
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge
                )
                if (character.films.isNotEmpty()) {
                    Text(
                        text = "Films: ${character.films.take(2).joinToString(", ")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DisneyCharTheme {
        Greeting("Android")
    }
}