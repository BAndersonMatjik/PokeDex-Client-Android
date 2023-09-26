package com.bmatjik.pokedex.viewmodel

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.bmatjik.pokedex.common.BaseViewModel
import com.bmatjik.pokedex.domain.CatchPokemonUsecase
import com.bmatjik.pokedex.domain.GetPokemonUsecase
import com.bmatjik.pokedex.domain.GetPokemonsUsecase
import com.bmatjik.pokedex.domain.ReleasePokemonUsecase
import com.bmatjik.pokedex.domain.RenamePokemonUsecase
import com.bmatjik.pokedex.domain.model.Pokemon
import com.bmatjik.pokedex.domain.model.PokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val catchPokemonUsecase: CatchPokemonUsecase,
    private val getPokemonUsecase: GetPokemonUsecase,
    private val renamePokemonUsecase: RenamePokemonUsecase,
    private val releasePokemonUsecase: ReleasePokemonUsecase
) : BaseViewModel<DetailPokemonEvent>() {

    private val mutableState = MutableStateFlow<DetailPokemonState>(DetailPokemonState())
    val state = mutableState

    override fun onEvent(event: DetailPokemonEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailPokemonEvent.GetDetailPokemon -> {
                    mutableState.update {
                        it.copy(isLoading = true)
                    }
                    getPokemonUsecase(event.pokemonId.toInt()).fold(onSuccess = { poke ->
                        mutableState.update {
                            it.copy(pokemon = poke, isLoading = false, isFromLocal = poke.fromLocal)
                        }
                    }, onFailure = { exception ->
                        mutableState.update {
                            it.copy(error = exception.message.toString(), isLoading = false)
                        }
                    })
                }

                is DetailPokemonEvent.CatchPokemon -> {
                    mutableState.update {
                        it.copy(isLoading = true)
                    }
                    catchPokemonUsecase().fold(onSuccess = { status ->
                        mutableState.update {
                            it.copy(isLoading = false, isObtain = status, isObtainRunning = false)
                        }
                    }, onFailure = { ex ->
                        mutableState.update {
                            it.copy(error = ex.message.toString(), isLoading = false, isObtainRunning = false)
                        }
                    })
                }

                is DetailPokemonEvent.RenamePokemon -> {
                    mutableState.update {
                        it.copy(isLoading = true)
                    }
                    renamePokemonUsecase(
                        event.name,
                        state.value.pokemon
                    ).fold(onSuccess = { result ->
                        mutableState.update {
                            it.copy(
                                isLoading = false,
                                pokemon = it.pokemon.copy(name = result),
                                isRename = true,
                            )
                        }
                    }, onFailure = { ex ->
                        mutableState.update {
                            it.copy(error = ex.message.toString(), isLoading = false, isrenameRunning = false)
                        }
                    })
                }

                is DetailPokemonEvent.ReleasePokemon -> {
                    mutableState.update {
                        it.copy(isLoading = true, isReleaseRunning = true)
                    }
                    releasePokemonUsecase(id = mutableState.value.pokemon.id).fold(onSuccess = { result ->
                        mutableState.update {
                            it.copy(isLoading = false, isRelease = result, isReleaseRunning = false)
                        }
                        onEvent(DetailPokemonEvent.GetDetailPokemon(pokemonId = mutableState.value.pokemon.id.toString()))
                    }, onFailure = { ex ->
                        mutableState.update {
                            it.copy(error = ex.message.toString(), isLoading = false, isReleaseRunning = false)
                        }
                    })
                }

                else -> {
                    Timber.e("Event Not Register")
                }
            }
        }
    }
}

data class DetailPokemonState(
    val isLoading: Boolean = false,
    val pokemon: PokemonDetail = PokemonDetail(),
    val error: String = "",
    val isObtainRunning:Boolean=false,
    val isObtain:Boolean =false,
    val isReleaseRunning:Boolean =false,
    val isRelease:Boolean =false,
    val isFromLocal:Boolean =false,
    val isrenameRunning:Boolean=false,
    val isRename:Boolean=false
)


sealed class DetailPokemonEvent {
    data class GetDetailPokemon(val pokemonId: String) : DetailPokemonEvent()
    object ReleasePokemon : DetailPokemonEvent()
    data class RenamePokemon(val name: String) : DetailPokemonEvent()
    object CatchPokemon : DetailPokemonEvent()
}