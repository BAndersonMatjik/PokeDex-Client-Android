package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Other(
    @SerialName("dream_world")
    val dreamWorld: DreamWorld? = null,
    @SerialName("home")
    val home: Home? = null,
    @SerialName("official-artwork")
    val officialArtwork: OfficialArtwork? = null
)