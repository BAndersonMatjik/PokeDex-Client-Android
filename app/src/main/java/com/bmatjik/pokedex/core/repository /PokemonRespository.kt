package com.bmatjik.pokedex.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bmatjik.pokedex.core.local.LocalDataSource
import com.bmatjik.pokedex.core.remote.PokemonPagingDataSource
import com.bmatjik.pokedex.core.remote.RemoteDataSource
import com.bmatjik.pokedex.domain.model.Pokemon
import com.bmatjik.pokedex.domain.model.PokemonDetail
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PokemonRespository {
    fun getPokemons(): Flow<PagingData<Pokemon>>
    suspend fun catchPokemon(): Result<Boolean>
    suspend fun getPokemon(id:Int): Result<PokemonDetail>
    suspend fun releasePokemon():Result<Int>
    suspend fun rename(name:String):Result<String>

    suspend fun deleteLocal(id:Int)
    suspend fun insertLocal(pokemonDetail: PokemonDetail)
    suspend fun getPokemonLocal(id: Int): Result<PokemonDetail>
    suspend fun getPokemonsLocal():Result<List<PokemonDetail>>
}

@ViewModelScoped
class PokemonRepositoryImpl @Inject constructor(
    private val pokemonPagingDataSource: PokemonPagingDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : PokemonRespository {
    override fun getPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(20, prefetchDistance = 2),
            pagingSourceFactory = {
                pokemonPagingDataSource
            }
        ).flow
    }

    override suspend fun catchPokemon(): Result<Boolean> {
        return remoteDataSource.catchPokemon()
    }

    override suspend fun getPokemon(id:Int): Result<PokemonDetail> {
       return remoteDataSource.getPokemon(id)
    }

    override suspend fun releasePokemon(): Result<Int> {
        return remoteDataSource.releasePokemon()
    }

    override suspend fun rename(name: String): Result<String> {
        return remoteDataSource.rename(name)
    }

    override suspend fun deleteLocal(id: Int) {
        return localDataSource.delete(id)
    }

    override suspend fun insertLocal(pokemonDetail: PokemonDetail) {
        return localDataSource.insert(pokemonDetail)
    }

    override suspend fun getPokemonLocal(id: Int): Result<PokemonDetail> {
        return localDataSource.getPokemon(id)
    }

    override suspend fun getPokemonsLocal(): Result<List<PokemonDetail>> {
        return localDataSource.getPokemons()
    }

}