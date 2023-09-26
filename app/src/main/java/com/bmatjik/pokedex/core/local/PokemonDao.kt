package com.bmatjik.pokedex.core.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneOrMore(vararg pokemonEntity: PokemonEntity)

    @Delete
    suspend fun delete(pokemonEntity: PokemonEntity)

    @Query("DELETE FROM my_pokemon WHERE id =:id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM my_pokemon WHERE id=:id")
    suspend fun findById(id: Int): PokemonEntity?

    @Query("SELECT * FROM my_pokemon")
    suspend fun findAll(): List<PokemonEntity>
}