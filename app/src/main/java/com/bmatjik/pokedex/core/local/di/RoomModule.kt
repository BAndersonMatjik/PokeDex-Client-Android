package com.bmatjik.pokedex.core.local.di

import android.content.Context
import androidx.room.Room
import com.bmatjik.pokedex.core.local.PokeDexDatabase
import com.bmatjik.pokedex.core.local.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): PokeDexDatabase =
        Room.databaseBuilder(context, PokeDexDatabase::class.java, PokeDexDatabase.DB_NAME).build()

    @Singleton
    @Provides
    fun providesPokemonDao(pokeDexDatabase: PokeDexDatabase): PokemonDao =
        pokeDexDatabase.pokemonDao()
}

