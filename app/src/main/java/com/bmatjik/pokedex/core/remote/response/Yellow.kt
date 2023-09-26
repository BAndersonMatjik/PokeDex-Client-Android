package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Yellow(
    @SerialName("back_default")
    val backDefault: String? = null,
    @SerialName("back_gray")
    val backGray: String? = null,
    @SerialName("back_transparent")
    val backTransparent: String? = null,
    @SerialName("front_default")
    val frontDefault: String? = null,
    @SerialName("front_gray")
    val frontGray: String? = null,
    @SerialName("front_transparent")
    val frontTransparent: String? = null
)