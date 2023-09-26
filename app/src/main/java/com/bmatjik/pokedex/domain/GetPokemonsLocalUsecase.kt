package com.bmatjik.pokedex.domain

import com.bmatjik.pokedex.core.repository.PokemonRespository
import com.bmatjik.pokedex.domain.model.PokemonDetail
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface GetPokemonsLocalUsecase {
    suspend operator fun invoke():Result<List<PokemonDetail>>
}
@ViewModelScoped
class GetPokemonsLocalUsecaseImpl @Inject constructor(
    private val pokemonRespository: PokemonRespository
):GetPokemonsLocalUsecase{
    override suspend fun invoke(): Result<List<PokemonDetail>> {
        return pokemonRespository.getPokemonsLocal()
    }

}