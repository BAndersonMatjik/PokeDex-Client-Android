package com.bmatjik.pokedex.core.remote

import com.bmatjik.pokedex.core.remote.request.RenamePokemonRequest
import com.bmatjik.pokedex.core.remote.response.BaseResponse
import com.bmatjik.pokedex.core.remote.response.PokemonDetailResponse
import com.bmatjik.pokedex.core.remote.response.PokemonsResponse
import com.bmatjik.pokedex.domain.model.Pokemon
import com.bmatjik.pokedex.domain.model.PokemonDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.nio.file.Paths
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

interface RemoteDataSource {
    suspend fun getPokemons(limitPage: Int, offset: Int): Result<List<Pokemon>>
    suspend fun getPokemon(id: Int): Result<PokemonDetail>
    suspend fun catchPokemon(): Result<Boolean>
    suspend fun releasePokemon():Result<Int>
    suspend fun rename(name:String):Result<String>
}

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    @Named("Pokedex") private val httpClient: HttpClient,
    @Named("Catcher") private val catchClient: HttpClient
) : RemoteDataSource {
    override suspend fun getPokemons(limitPage: Int, offset: Int): Result<List<Pokemon>> {
        var url = ApiRoutes.POKEMONS_ENDPOINT
        url = url.replace("{offset}", offset.toString())
        url = url.replace("{limit}", limitPage.toString())

        val result:Result<PokemonsResponse> = kotlin.runCatching {
            httpClient.get(urlString = url).body()
        }

        result.exceptionOrNull()?.apply {
            Timber.tag(Companion.TAG).e("getPokemons: $this")
        }
        val mappedData = result.getOrThrow()?.results?.map {
            val paths = Paths.get(it?.url ?: "")
            val pathPosition = paths.nameCount - 1
            val indexPokemon = paths.getName(pathPosition).toString()
            Pokemon(
                id = indexPokemon.toInt(),
                name = it?.name ?: "",
                imageUrl = ApiRoutes.BASE_IMAGE_URL.replace("{image}", indexPokemon)
            )
        } ?: listOf()

        return Result.success(mappedData)
    }

    override suspend fun getPokemon(id: Int): Result<PokemonDetail> {
        var url = ApiRoutes.POKEMON_ENDPOINT
        url = url.replace("{id}", id.toString())

        val result:Result<PokemonDetailResponse> = kotlin.runCatching {
            httpClient.get(url).body()
        }
        result.exceptionOrNull()?.apply {
            Timber.tag(Companion.TAG).e("getPokemon: $this")
        }
        val mappedData = result.getOrThrow().let {
            PokemonDetail(
                id = id,
                imageUrl = ApiRoutes.BASE_IMAGE_URL.replace("{image}", id.toString()),
                weight = it.weight ?: 0,
                name = it.name ?: "",
                species = it.species?.name ?: "",
                moves = it.moves?.map {
                    it.move?.name ?: ""
                } ?: listOf(),
                types = it.types?.map {
                    it.type?.name ?: ""
                } ?: listOf(),
                abilitys = it.abilities?.map {
                    it.ability?.name ?: ""
                } ?: listOf()
            )
        }
        return Result.success(mappedData)
    }

    override suspend fun catchPokemon(): Result<Boolean> {
        val url = ApiRoutes.CATCHER_CATCH_ENDPOINT
        val result:Result<BaseResponse<Boolean>> = kotlin.runCatching {
            catchClient.get(url).body()
        }
        val mappedData = result.getOrThrow().let {
            if (it.status) {
                Result.success(it.result)
            } else {
                Result.failure(Exception("Failed Catch Pokemon"))
            }
        }
        return mappedData
    }

    override suspend fun releasePokemon(): Result<Int> {
        var url = ApiRoutes.CATCHER_RELEASE_ENDPOINT
        val result:Result<BaseResponse<Int>> = kotlin.runCatching {
            catchClient.get(url).body()
        }
        val mappedData = result.getOrThrow().let {
            if (it.status) {
                Result.success(it.result)
            } else {
                Result.failure(Exception("Release Pokemon"))
            }
        }
        return mappedData
    }

    override suspend fun rename(name: String): Result<String> {
        val url = ApiRoutes.CATCHER_RENAME_ENDPOINT
        val params = RenamePokemonRequest(name)
        val result:Result<BaseResponse<String>> = kotlin.runCatching {
            catchClient.post(url){
                contentType(ContentType.Application.Json)
                setBody(params)
            }.body()
        }
        val mappedData = result.getOrThrow().let {
            if (it.status) {
                Result.success(it.result)
            } else {
                Result.failure(Exception("Rename Pokemon"))
            }
        }
        return mappedData
    }

    companion object {
        private const val TAG = "RemoteDataSource"
    }

}