package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypeX(
    @SerialName("name")
    val name: String? = null,
    @SerialName("url")
    val url: String? = null
)