package com.bmatjik.pokedex.core.di

import com.bmatjik.pokedex.core.repository.PokemonRepositoryImpl
import com.bmatjik.pokedex.core.repository.PokemonRespository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl):PokemonRespository
}