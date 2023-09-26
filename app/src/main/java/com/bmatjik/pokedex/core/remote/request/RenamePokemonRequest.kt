package com.bmatjik.pokedex.core.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RenamePokemonRequest(
    @SerialName("name")
    val name:String
)
