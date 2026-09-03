package com.edcode.disneychar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DisneyCharacterEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DisneyDatabase : RoomDatabase() {
    abstract fun disneyDao(): DisneyDao
}
