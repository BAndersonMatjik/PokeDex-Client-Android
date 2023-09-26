package com.bmatjik.pokedex.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bmatjik.pokedex.R
import com.bmatjik.pokedex.domain.model.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonItem(
    pokemon: Pokemon,
    cardColors: CardColors = CardDefaults.cardColors(containerColor = Color.White),
    modifier: Modifier = Modifier,
    onItemClick: (Pokemon) -> Unit = {}
) {
    Card(
        modifier = modifier.shadow(
            ambientColor = Color.Gray.copy(alpha = 0.8F),
            elevation = 10.dp
        ),
        colors = cardColors,
        shape = CardDefaults.elevatedShape,
        onClick = {
            onItemClick(pokemon)
        },
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                placeholder = debugPlaceholder(R.drawable.ic_launcher_foreground)
            )
            Text(
                text = pokemon.name,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
@Preview
fun PokemonItemPreview() {
    PokemonItem(
        pokemon = Pokemon(
            id = 6893,
            name = "Aisha Oneal",
            imageUrl = "https://search.yahoo.com/search?p=hinc"
        )
    )
}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }
