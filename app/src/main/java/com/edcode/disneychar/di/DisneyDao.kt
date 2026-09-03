package com.edcode.disneychar.di

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edcode.disneychar.data.local.DisneyCharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DisneyDao {
    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharacters(): Flow<List<DisneyCharacterEntity>>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%'")
    fun searchCharacters(query: String): Flow<List<DisneyCharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<DisneyCharacterEntity>)

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}