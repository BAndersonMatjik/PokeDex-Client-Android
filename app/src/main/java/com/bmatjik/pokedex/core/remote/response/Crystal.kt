package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crystal(
    @SerialName("back_default")
    val backDefault: String? = null,
    @SerialName("back_shiny")
    val backShiny: String? = null,
    @SerialName("back_shiny_transparent")
    val backShinyTransparent: String? = null,
    @SerialName("back_transparent")
    val backTransparent: String? = null,
    @SerialName("front_default")
    val frontDefault: String? = null,
    @SerialName("front_shiny")
    val frontShiny: String? = null,
    @SerialName("front_shiny_transparent")
    val frontShinyTransparent: String? = null,
    @SerialName("front_transparent")
    val frontTransparent: String? = null
)