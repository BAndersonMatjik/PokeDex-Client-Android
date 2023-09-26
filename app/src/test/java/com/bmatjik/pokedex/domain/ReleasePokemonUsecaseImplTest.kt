package com.bmatjik.pokedex.domain

import com.bmatjik.pokedex.core.repository.PokemonRespository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class ReleasePokemonUsecaseImplTest {
    private val pokemonrepository=  mockk<PokemonRespository>()

    lateinit var releasePokemonUsecaseImpl: ReleasePokemonUsecaseImpl
    @Before
    fun setUp() {
        releasePokemonUsecaseImpl= ReleasePokemonUsecaseImpl(pokemonRepository = pokemonrepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testPrimeNumber(){
        runTest {
            coEvery { pokemonrepository.releasePokemon() } returns Result.success(13)
            coEvery { pokemonrepository.deleteLocal(88) }
            releasePokemonUsecaseImpl(88).getOrNull()?.let {
                assertEquals(true,it)
            }
        }
    }
    @Test
    fun testPrimeNumberNot(){
        runTest {
            coEvery { pokemonrepository.releasePokemon() } returns Result.success(15)
            coEvery { pokemonrepository.deleteLocal(88) }
            releasePokemonUsecaseImpl(88).getOrNull()?.let {
                assertEquals(false,it)
            }
        }
    }
}