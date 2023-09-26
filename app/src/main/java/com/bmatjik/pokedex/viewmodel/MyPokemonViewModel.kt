package com.bmatjik.pokedex.viewmodel

import androidx.lifecycle.viewModelScope
import com.bmatjik.pokedex.common.BaseViewModel
import com.bmatjik.pokedex.domain.GetPokemonsLocalUsecase
import com.bmatjik.pokedex.domain.model.PokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPokemonViewModel @Inject constructor(
    private val getPokemonsLocalUsecase: GetPokemonsLocalUsecase
): BaseViewModel<MyPokemonEvent>() {
    private val mutableState = MutableStateFlow(MyPokemonState())
    val state = mutableState
    init {
        onEvent(MyPokemonEvent.GetPokemons)
    }
    override fun onEvent(event: MyPokemonEvent) {
       when(event){
           is MyPokemonEvent.GetPokemons ->{
                viewModelScope.launch {
                    getPokemonsLocalUsecase().fold(onSuccess = {result->
                        mutableState.update {
                            it.copy(pokemons = result)
                        }
                    }, onFailure = {
                        Timber.e(it)
                    })
                }
           }
       }
    }
}

data class MyPokemonState(
     val pokemons:List<PokemonDetail> = listOf()
)
sealed class MyPokemonEvent(){
    object GetPokemons:MyPokemonEvent()
}