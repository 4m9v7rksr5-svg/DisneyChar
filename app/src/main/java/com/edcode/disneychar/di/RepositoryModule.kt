package com.edcode.disneychar.di

import com.edcode.disneychar.data.DisneyRepositoryImpl
import com.edcode.disneychar.domain.DisneyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDisneyRepository(
        disneyRepositoryImpl: DisneyRepositoryImpl
    ): DisneyRepository
}
