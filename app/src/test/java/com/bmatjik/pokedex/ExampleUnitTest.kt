package com.bmatjik.pokedex

import android.net.Uri
import io.ktor.http.Url
import io.mockk.InternalPlatformDsl.toArray
import org.junit.Test

import org.junit.Assert.*
import java.net.URI
import java.nio.file.Paths

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val test="https://pokeapi.co/api/v2/pokemon/1/"
//        val url = Uri.parse(test)
//        url.pathSegments.apply {
//            println(this)
//        }
        Paths.get(test).apply {
            println(getName(this.nameCount-1))
        }


    }
}