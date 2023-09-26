package com.bmatjik.pokedex.core.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Versions(
    @SerialName("generation-i")
    val generationI: GenerationI? = GenerationI(),
    @SerialName("generation-ii")
    val generationIi: GenerationIi? = GenerationIi(),
    @SerialName("generation-iii")
    val generationIii: GenerationIii? = GenerationIii(),
    @SerialName("generation-iv")
    val generationIv: GenerationIv? = GenerationIv(),
    @SerialName("generation-v")
    val generationV: GenerationV? = GenerationV(),
    @SerialName("generation-vi")
    val generationVi: GenerationVi? = GenerationVi(),
    @SerialName("generation-vii")
    val generationVii: GenerationVii? = GenerationVii(),
    @SerialName("generation-viii")
    val generationViii: GenerationViii? = GenerationViii()
)