package com.edcode.disneychar.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.edcode.disneychar.R
import com.edcode.disneychar.domain.DisneyCharacter

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
