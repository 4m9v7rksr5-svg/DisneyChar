package com.edcode.disneychar.di

import android.content.Context
import androidx.room.Room
import com.edcode.disneychar.db.DisneyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): DisneyDatabase {
            return Room.databaseBuilder(
                context,
                DisneyDatabase::class.java,
                "disney_db"
            ).build()
        }

        @Provides
        fun provideDisneyDao(db: DisneyDatabase): DisneyDao = db.disneyDao()
    }
