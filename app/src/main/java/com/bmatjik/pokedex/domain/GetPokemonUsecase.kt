package com.bmatjik.pokedex.domain

import androidx.paging.PagingData
import com.bmatjik.pokedex.core.repository.PokemonRespository
import com.bmatjik.pokedex.domain.model.Pokemon
import com.bmatjik.pokedex.domain.model.PokemonDetail
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPokemonUsecase {
    suspend operator fun invoke(id: Int): Result<PokemonDetail>
}

@ViewModelScoped
class GetPokemonUsecaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRespository
) : GetPokemonUsecase {

    override suspend fun invoke(id: Int): Result<PokemonDetail> {
        pokemonRepository.getPokemonLocal(id).fold(onSuccess = {
            return Result.success(it.copy(fromLocal = true))
        }, onFailure = {
            return pokemonRepository.getPokemon(id)
        }
        )

    }

}