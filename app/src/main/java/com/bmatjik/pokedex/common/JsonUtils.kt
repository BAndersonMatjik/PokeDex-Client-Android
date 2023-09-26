package com.bmatjik.pokedex.common

import kotlinx.serialization.json.Json

object JsonUtils {
    val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }
}