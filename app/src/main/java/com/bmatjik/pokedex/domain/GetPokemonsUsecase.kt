package com.bmatjik.pokedex.domain

import androidx.paging.PagingData
import com.bmatjik.pokedex.core.remote.PokemonPagingDataSource
import com.bmatjik.pokedex.core.repository.PokemonRespository
import com.bmatjik.pokedex.domain.model.Pokemon
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface GetPokemonsUsecase {
    operator fun invoke(): Flow<PagingData<Pokemon>>
}

@ViewModelScoped
class GetPokemonsUsecaseImpl @Inject constructor(private val pokemonRespository: PokemonRespository):GetPokemonsUsecase{
    override fun invoke(): Flow<PagingData<Pokemon>> {
        return pokemonRespository.getPokemons()
    }

}