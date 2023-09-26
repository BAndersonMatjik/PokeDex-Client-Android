package com.bmatjik.pokedex.core.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id:Int?=null,
    @ColumnInfo(name = "pokemon_name")
    val pokemonName:String="",
    @ColumnInfo(name = "json_content")
    val jsonContent:String=""
)
