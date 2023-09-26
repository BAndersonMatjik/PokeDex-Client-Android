package com.bmatjik.pokedex.core.local

import com.bmatjik.pokedex.common.JsonUtils
import com.bmatjik.pokedex.domain.model.PokemonDetail
import kotlinx.serialization.decodeFromString
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface LocalDataSource {
    suspend fun insert(pokemonDetail: PokemonDetail)
    suspend fun getPokemon(id: Int): Result<PokemonDetail>
    suspend fun delete(id: Int)
    suspend fun getPokemons():Result<List<PokemonDetail>>
}

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val dao: PokemonDao
) : LocalDataSource {
    override suspend fun insert(pokemonDetail: PokemonDetail) {
        pokemonDetail.let {
            PokemonEntity(
                id = it.id,
                pokemonName = it.name,
                jsonContent = JsonUtils.json.encodeToString(
                    PokemonDetail.serializer(),
                    pokemonDetail
                )
            )
        }.apply {
            dao.insertOneOrMore(this)
        }
    }

    override suspend fun getPokemon(id: Int): Result<PokemonDetail> {
        return dao.findById(id)?.let {
            val result = JsonUtils.json.decodeFromString<PokemonDetail>(it?.jsonContent ?: "")
            Result.success(result)
        } ?: Result.failure(Exception("Not Found"))
    }

    override suspend fun delete(id: Int) {
        getPokemon(id).fold(onSuccess = {
            dao.deleteById(id)
        }, onFailure = {
            Timber.e(it)
        })
    }

    override suspend fun getPokemons(): Result<List<PokemonDetail>> {
        return dao.findAll().map {
            val result = JsonUtils.json.decodeFromString<PokemonDetail>(it?.jsonContent ?: "")
            result
        }.let {
            Result.success(it)
        }
    }


}