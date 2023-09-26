package com.bmatjik.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bmatjik.pokedex.common.BaseViewModel
import com.bmatjik.pokedex.domain.GetPokemonsUsecase
import com.bmatjik.pokedex.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonsUsecase: GetPokemonsUsecase
):BaseViewModel<HomeEventState>() {
    private val _pokemonState: MutableStateFlow<PagingData<Pokemon>> =
        MutableStateFlow(value = PagingData.empty())
    val pokemonState: MutableStateFlow<PagingData<Pokemon>> get() = _pokemonState

    init {
        onEvent(HomeEventState.GetPokemons)
    }

    override fun onEvent(event: HomeEventState) {
        when(event){
            is HomeEventState->{
                getPokemon()
            }
            else ->{

            }
        }
    }

    private fun getPokemon(){
        viewModelScope.launch {
            getPokemonsUsecase().distinctUntilChanged().cachedIn(viewModelScope).collectLatest {data->
                _pokemonState.update {
                    data
                }
            }
        }
    }
}

sealed class HomeEventState{
    object GetPokemons:HomeEventState()
}