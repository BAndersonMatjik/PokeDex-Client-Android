package com.bmatjik.pokedex.domain.di

import com.bmatjik.pokedex.domain.CatchPokemonUsecase
import com.bmatjik.pokedex.domain.CatchPokemonUsecaseImpl
import com.bmatjik.pokedex.domain.GetPokemonUsecase
import com.bmatjik.pokedex.domain.GetPokemonUsecaseImpl
import com.bmatjik.pokedex.domain.GetPokemonsLocalUsecase
import com.bmatjik.pokedex.domain.GetPokemonsLocalUsecaseImpl
import com.bmatjik.pokedex.domain.GetPokemonsUsecase
import com.bmatjik.pokedex.domain.GetPokemonsUsecaseImpl
import com.bmatjik.pokedex.domain.ReleasePokemonUsecase
import com.bmatjik.pokedex.domain.ReleasePokemonUsecaseImpl
import com.bmatjik.pokedex.domain.RenamePokemonUsecase
import com.bmatjik.pokedex.domain.RenamePokemonUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class UsecaseModule {
    @Binds
    abstract fun bindsGetPokemonsUsecase(getPokemonsUsecaseImpl: GetPokemonsUsecaseImpl):GetPokemonsUsecase
    @Binds
    abstract fun bindsGetPokemonsLocalUsecase(getPokemonsUsecaseImpl: GetPokemonsLocalUsecaseImpl): GetPokemonsLocalUsecase
    @Binds
    abstract fun bindsGetPokemonUsecase(getPokemonUsecase: GetPokemonUsecaseImpl):GetPokemonUsecase
    @Binds
    abstract fun bindsCatchPokemonUsecase(catchPokemonUsecaseImpl: CatchPokemonUsecaseImpl):CatchPokemonUsecase
    @Binds
    abstract fun bindsRenamePokemonUsecase(renamePokemonUsecaseImpl:RenamePokemonUsecaseImpl):RenamePokemonUsecase
    @Binds
    abstract fun bindsReleasePokemonUsecase(releasePokemonUsecaseImpl: ReleasePokemonUsecaseImpl):ReleasePokemonUsecase
}