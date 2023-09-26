package com.bmatjik.pokedex.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetail(
    val id:Int =0,
    val imageUrl: String = "",
    val weight: Int = 0,
    val name: String = "",
    val species: String = "",
    val moves: List<String> = listOf(),
    val types: List<String> = listOf(),
    val abilitys: List<String> = listOf(),
    val fromLocal:Boolean =false,
)
