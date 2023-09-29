package com.bmatjik.pokedex.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bmatjik.pokedex.domain.model.Pokemon
import com.bmatjik.pokedex.domain.model.PokemonDetail
import com.bmatjik.pokedex.ui.theme.PokeDexTheme
import com.bmatjik.pokedex.viewmodel.HomeViewModel
import com.bmatjik.pokedex.viewmodel.MyPokemonViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MyPokemonScreen(
    viewmodel: MyPokemonViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onPokeItemClick: (Pokemon) -> Unit = {}
) {
    val uiState = viewmodel.state.collectAsStateWithLifecycle()
    MyPokemonUi(
        pokemons = uiState.value.pokemons,
        onPokeItemClick = onPokeItemClick
    )
}

@Composable
fun MyPokemonUi(
    modifier: Modifier = Modifier,
    onPokeItemClick: (Pokemon) -> Unit = {},
    pokemons: List<PokemonDetail> = listOf()
) {
    Column(modifier = modifier) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(pokemons.size) { index ->
                pokemons[index].apply {
                    PokemonItem(
                        pokemon = Pokemon(this.id, this.name, this.imageUrl),
                        onItemClick = onPokeItemClick
                    )
                }
            }
        }
    }
}


@Composable
@Preview(device = Devices.PIXEL_2, showSystemUi = true, showBackground = true)
fun MyPokemonUiPreview() {
    PokeDexTheme {
        MyPokemonUi()
    }
}