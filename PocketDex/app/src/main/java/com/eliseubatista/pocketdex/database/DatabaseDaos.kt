package com.eliseubatista.pocketdex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Query("SELECT * FROM databasepokemon")
    fun getPokemons(): List<DatabasePokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemons: DatabasePokemon)
}

@Dao
interface TypesDao {

    @Query("SELECT * FROM databasetypes")
    fun getTypes(): List<DatabaseTypes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg types: DatabaseTypes)
}
