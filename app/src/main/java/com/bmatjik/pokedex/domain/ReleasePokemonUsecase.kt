package com.bmatjik.pokedex.domain

import com.bmatjik.pokedex.core.repository.PokemonRespository
import dagger.hilt.android.scopes.ViewModelScoped
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface ReleasePokemonUsecase {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

@ViewModelScoped
class ReleasePokemonUsecaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRespository
) : ReleasePokemonUsecase {
    override suspend fun invoke(id: Int): Result<Boolean> {
        var flag = true
        val result = pokemonRepository.releasePokemon()
        result.exceptionOrNull()?.let {
            return Result.failure(it)
        }
        result.getOrNull()?.let { probability ->
            if (probability <= 1) {
                return Result.success(false)
            }
            for (i in 2..probability / 2) {
                if (probability % i == 0) {
                    flag = false
                    break
                }
            }
        }
        try {
            if (flag) {
                pokemonRepository.deleteLocal(id = id)
            }
        } catch (e: Exception) {
           Timber.e(e)
        }
        return Result.success(flag)
    }
}