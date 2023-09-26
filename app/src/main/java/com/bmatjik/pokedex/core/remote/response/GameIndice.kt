package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameIndice(
    @SerialName("game_index")
    val gameIndex: Int? = null,
    @SerialName("version")
    val version: Version? = null
)