package com.edcode.disneychar.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val FAVORITES_KEY = stringSetPreferencesKey("favorite_ids")

    // Expone los favoritos como un Flow de Set de Strings (IDs)
    val favoriteIds: Flow<Set<String>> = dataStore.data.map { preferences ->
        preferences[FAVORITES_KEY] ?: emptySet()
    }

    suspend fun toggleFavorite(id: Int) {
        val idString = id.toString()
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            if (currentFavorites.contains(idString)) {
                preferences[FAVORITES_KEY] = currentFavorites - idString // Elimina
            } else {
                preferences[FAVORITES_KEY] = currentFavorites + idString // Agrega
            }
        }
    }
}