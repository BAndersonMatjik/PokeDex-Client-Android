package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIv(
    @SerialName("diamond-pearl")
    val diamondPearl: DiamondPearl? = null,
    @SerialName("heartgold-soulsilver")
    val heartgoldSoulsilver: HeartgoldSoulsilver? = null,
    @SerialName("platinum")
    val platinum: Platinum? = null
)