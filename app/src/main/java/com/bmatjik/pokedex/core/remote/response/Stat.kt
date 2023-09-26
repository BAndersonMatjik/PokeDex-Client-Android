package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    @SerialName("base_stat")
    val baseStat: Int? = 0,
    @SerialName("effort")
    val effort: Int? = 0,
    @SerialName("stat")
    val stat: StatX? = StatX()
)