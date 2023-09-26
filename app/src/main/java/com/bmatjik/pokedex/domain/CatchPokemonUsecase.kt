package com.bmatjik.pokedex.domain

import com.bmatjik.pokedex.core.repository.PokemonRespository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface CatchPokemonUsecase {
    suspend operator fun invoke(): Result<Boolean>
}

@ViewModelScoped
class CatchPokemonUsecaseImpl @Inject constructor(private val pokemonRepository: PokemonRespository) :
    CatchPokemonUsecase {
    override suspend fun invoke(): Result<Boolean> {
        val resultCatch = pokemonRepository.catchPokemon()
        resultCatch.exceptionOrNull()?.apply {
            return Result.failure(this)
        }
        return Result.success(resultCatch.getOrDefault(false))
    }
}