package com.bmatjik.pokedex.core.remote.di

import com.bmatjik.pokedex.core.remote.ApiRoutes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.client.request.host
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object KtorModule {


    @Provides
    @Singleton
    @Named("Pokedex")
    fun provideKtorClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys =true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("Logger Ktor =>").v(message)
                    }
                }
                level = LogLevel.BODY
            }

            install(ResponseObserver) {
                onResponse {
                    Timber.tag("HTTP status:").d(it.status.value.toString())
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                //set base url
                host = ApiRoutes.BASE_URL
                url.protocol = URLProtocol.HTTPS
//                url.parameters.append("key","${ApiRoutes.API_KEY}")
            }
        }
    }

    @Provides
    @Singleton
    @Named("Catcher")
    fun provideKtorClientCatcher(): HttpClient {
        return HttpClient {
            install(plugin = ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys=true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("Logger Ktor =>").v(message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse {
                    Timber.tag("HTTP status:").d(it.status.value.toString())
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                //set base url
                host = ApiRoutes.BASE_CATCHER
                url.protocol = URLProtocol.HTTP
//                url.parameters.append("key","${ApiRoutes.API_KEY}")
            }
        }
    }


}