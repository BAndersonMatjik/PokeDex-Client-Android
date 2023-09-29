package com.bmatjik.pokedex.domain

import com.bmatjik.pokedex.core.repository.PokemonRespository
import com.bmatjik.pokedex.domain.model.PokemonDetail
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface RenamePokemonUsecase {
    suspend operator fun invoke(pokemonName: String, pokemonDetail: PokemonDetail): Result<String>
}

@ViewModelScoped
class RenamePokemonUsecaseImpl @Inject constructor(private val pokemonRepository: PokemonRespository) :
    RenamePokemonUsecase {
    override suspend fun invoke(pokemonName: String, pokemonDetail: PokemonDetail): Result<String> {
        val pokemonName = if (pokemonDetail.name.contains("-")){
            (pokemonName +"-"+ pokemonDetail.name.split("-").lastOrNull()) ?: ""
        }else{
            pokemonName
        }
        pokemonRepository.rename(pokemonName).fold(
            onSuccess = {
                val result = pokemonDetail.copy(name = it)
                pokemonRepository.insertLocal(result)
                return Result.success(it)
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

}