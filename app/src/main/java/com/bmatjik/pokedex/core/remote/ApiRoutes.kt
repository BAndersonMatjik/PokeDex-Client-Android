package com.bmatjik.pokedex.core.remote

object ApiRoutes {
    const val BASE_URL = "pokeapi.co/api/v2"

    const val BASE_CATCHER= "10.0.2.2:8080"

    const val CATCHER_CATCH_ENDPOINT = "catch"
    const val CATCHER_RELEASE_ENDPOINT = "release"
    const val CATCHER_RENAME_ENDPOINT = "rename"
    const val BASE_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{image}.png"

    const val POKEMONS_ENDPOINT = "pokemon?offset={offset}&limit={limit}"
    const val POKEMON_ENDPOINT = "pokemon/{id}"
}