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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bmatjik.pokedex.domain.model.Pokemon
import com.bmatjik.pokedex.ui.theme.PokeDexTheme
import com.bmatjik.pokedex.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(
    viewmodel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onPokeItemClick: (Pokemon) -> Unit = {}
) {
    val pokemonPagingItems: LazyPagingItems<Pokemon> =
        viewmodel.pokemonState.collectAsLazyPagingItems()
    HomeUi(pokemonPagingItems = pokemonPagingItems, onPokeItemClick = onPokeItemClick)
}

@Composable
fun HomeUi(
    modifier: Modifier = Modifier,
    onPokeItemClick: (Pokemon) -> Unit = {},
    pokemonPagingItems: LazyPagingItems<Pokemon>
) {
    Column(modifier = modifier) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(pokemonPagingItems.itemCount) { index ->
                pokemonPagingItems[index]?.apply {
                    PokemonItem(pokemon = this, onItemClick = onPokeItemClick)
                }
            }

            pokemonPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                LinearProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                            }
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = pokemonPagingItems.loadState.refresh as LoadState.Error
                        item {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Error :: ${error.error.message.toString()}",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                    pokemonPagingItems.retry()
                                }, content = {
                                    Text(text = "Retry")
                                })

                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                LinearProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            Column {
                                Text(
                                    text = "Not Found More Data",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Button(onClick = {
                                    pokemonPagingItems.retry()
                                }, content = {
                                    Text(text = "Retry")
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview(device = Devices.PIXEL_2, showSystemUi = true, showBackground = true)
fun HomeUiPreview() {
    val pokemonItems = MutableStateFlow(
        value = PagingData.from(
            listOf(
                Pokemon(
                    id = 5676,
                    name = "Daphne Bright",
                    imageUrl = "https://www.google.com/#q=postea"
                ),
                Pokemon(
                    id = 7138,
                    name = "Charity Farmer",
                    imageUrl = "https://duckduckgo.com/?q=id"
                )
            )
        )
    )

    PokeDexTheme {
        HomeUi(pokemonPagingItems = pokemonItems.collectAsLazyPagingItems())
    }
}