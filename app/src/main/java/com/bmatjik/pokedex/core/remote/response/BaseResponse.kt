package com.bmatjik.pokedex.core.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val errorMessage: String?=null,
    val status: Boolean,
    val result: T
)