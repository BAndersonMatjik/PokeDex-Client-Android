package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Home(
    @SerialName("front_default")
    val frontDefault: String? = null,

    @SerialName("front_shiny")
    val frontShiny: String? = null,

)