package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIi(
    @SerialName("crystal")
    val crystal: Crystal? = null,
    @SerialName("gold")
    val gold: Gold? = null,
    @SerialName("silver")
    val silver: Silver? = null
)