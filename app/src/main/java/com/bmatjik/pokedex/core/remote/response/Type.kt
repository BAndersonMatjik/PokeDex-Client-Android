package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Type(
    @SerialName("slot")
    val slot: Int? = 0,
    @SerialName("type")
    val type: TypeX? = TypeX()
)