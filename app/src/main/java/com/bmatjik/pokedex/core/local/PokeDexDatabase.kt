package com.bmatjik.pokedex.core.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity::class], exportSchema = false, version = 1)
abstract class PokeDexDatabase:RoomDatabase() {
    companion object{
        const val DB_NAME = "pokedex_db"
    }
    abstract fun pokemonDao():PokemonDao
}