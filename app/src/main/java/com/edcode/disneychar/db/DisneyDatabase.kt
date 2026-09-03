package com.edcode.disneychar.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.edcode.disneychar.di.DisneyDao
import com.edcode.disneychar.data.local.DisneyCharacterEntity

@Database(entities = [DisneyCharacterEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DisneyDatabase : RoomDatabase() {
    abstract fun disneyDao(): DisneyDao
}
