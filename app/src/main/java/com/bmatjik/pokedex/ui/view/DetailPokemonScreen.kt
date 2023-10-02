package com.bmatjik.pokedex.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bmatjik.pokedex.R
import com.bmatjik.pokedex.domain.model.PokemonDetail
import com.bmatjik.pokedex.ui.theme.PokeDexTheme
import com.bmatjik.pokedex.viewmodel.CatchState
import com.bmatjik.pokedex.viewmodel.DetailPokemonEvent
import com.bmatjik.pokedex.viewmodel.DetailPokemonState
import com.bmatjik.pokedex.viewmodel.DetailPokemonViewModel
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun DetailPokemonScreen(
    id: String = "",
    viewmodel: DetailPokemonViewModel = hiltViewModel(),
    onBack: (Unit) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewmodel.onEvent(DetailPokemonEvent.GetDetailPokemon(id))
    }

    val context = LocalContext.current
    val uiState = viewmodel.state.collectAsStateWithLifecycle()
    uiState.value.errorMessage?.apply {
        LaunchedEffect(key1 = this) {
            Toast.makeText(context, this@apply, Toast.LENGTH_SHORT)
                .show()

        }
    }
    DetailPokemonUi(pokemonDetail = uiState.value, onClickCatch = {
        viewmodel.onEvent(DetailPokemonEvent.CatchPokemon)
    }, onClickRename = {
        viewmodel.onEvent(DetailPokemonEvent.RenamePokemon(it))
    }, onClickRelease = {
        viewmodel.onEvent(DetailPokemonEvent.ReleasePokemon)
    })
}

@Composable
fun DetailPokemonUi(
    pokemonDetail: DetailPokemonState,
    onClickCatch: (Int) -> Unit = {},
    onClickRename: (String) -> Unit = {},
    onClickRelease: () -> Unit = {},
) {
    val openDialog = remember { mutableStateOf(false) }
    val textButton = remember {
        mutableStateOf("Catch Pokemon")
    }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        RenamePokemonDialog(
            onClickRename = onClickRename,
            onClickRelease = onClickRelease,
            openDialog = openDialog.value,
            pokemonName = pokemonDetail.pokemon.name
        )


        LaunchedEffect(key1 = pokemonDetail.state,key2 = pokemonDetail.pokemon) {
            when (pokemonDetail.state) {
                is CatchState.None -> {

                }

                is CatchState.Obtain -> {
                    Toast.makeText(context, "Saved In My Pokemon", Toast.LENGTH_SHORT)
                        .show()
                    textButton.value = "Rename Pokemon"
                }

                is CatchState.Rename -> {
                    Toast.makeText(context, "Rename Pokemon", Toast.LENGTH_SHORT)
                        .show()
                    openDialog.value = false
                }

                is CatchState.Release -> {
                    Toast.makeText(context, "Release Has Done", Toast.LENGTH_SHORT)
                        .show()
                    openDialog.value = false
                }

                else -> {

                }
            }
        }
        if (pokemonDetail.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
        } else {
            Button(
                onClick = {
                    if (pokemonDetail.state is CatchState.Obtain|| pokemonDetail.state is CatchState.Rename || pokemonDetail.isFromLocal) {
                        openDialog.value = true
                    } else {
                        onClickCatch(pokemonDetail.pokemon.id)
                    }
                }, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(6.dp)
                    .fillMaxWidth()
            ) {
                Text(text = textButton.value)
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonDetail.pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemonDetail.pokemon.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                placeholder = debugPlaceholder(R.drawable.ic_launcher_foreground)
            )
            Text(
                text = pokemonDetail.pokemon.name,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Weight ${pokemonDetail.pokemon.weight}",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "Species ${pokemonDetail.pokemon.species}",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = "Ability",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.labelMedium
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(pokemonDetail.pokemon.abilitys.count()) {
                    pokemonDetail.pokemon.abilitys[it].apply {
                        Card {
                            Text(
                                text = this@apply,
                                modifier = Modifier.padding(6.dp),
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Black,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
            Text(
                text = "Moves",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.labelMedium
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(pokemonDetail.pokemon.moves.count()) {
                    pokemonDetail.pokemon.moves[it].apply {
                        Card {
                            Text(
                                text = this@apply,
                                modifier = Modifier.padding(6.dp),
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Black,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
            Text(
                text = "Types",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.labelMedium
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(pokemonDetail.pokemon.types.count()) {
                    pokemonDetail.pokemon.types[it].apply {
                        Card {
                            Text(
                                text = this@apply,
                                modifier = Modifier.padding(6.dp),
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Black,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }

}

@Preview(device = Devices.PIXEL_2, showBackground = true, showSystemUi = true)
@Composable
fun DetailPokemonUiPreview() {
    PokeDexTheme {
        DetailPokemonUi(
            pokemonDetail = DetailPokemonState(
                pokemon = PokemonDetail(
                    id = 2457,
                    imageUrl = "http://www.bing.com/search?q=percipit",
                    weight = 6832,
                    name = "Teddy Burgess",
                    species = "cubilia",
                    moves = listOf(),
                    types = listOf(),
                    abilitys = listOf()
                )
            )
        )
    }
}