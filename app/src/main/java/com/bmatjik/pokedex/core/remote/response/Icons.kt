package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Icons(
    @SerialName("front_default")
    val frontDefault: String? = null,
)