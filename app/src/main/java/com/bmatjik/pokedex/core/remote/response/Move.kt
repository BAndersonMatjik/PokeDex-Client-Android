package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Move(
    @SerialName("move")
    val move: MoveX? = MoveX(),
    @SerialName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetail>? = listOf()
)