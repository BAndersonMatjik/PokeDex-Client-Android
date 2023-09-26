package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationViii(
    @SerialName("icons")
    val icons: Icons? = Icons()
)