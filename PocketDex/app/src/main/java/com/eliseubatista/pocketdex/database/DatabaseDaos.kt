package com.eliseubatista.pocketdex.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TypesDao {

    @Query("select * from databasetypes")
    fun getTypes(): LiveData<List<DatabaseTypes>>

    @Query("select * from databasetypes where name = :name")
    fun getTypeByName(name: String): DatabaseTypes?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg types: DatabaseTypes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(types: List<DatabaseTypes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type: DatabaseTypes)
}

@Dao
interface PokemonDao {

    @Query("select * from databasepokemon")
    fun getPokemons(): LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name = :name")
    fun getPokemonByName(name: String): DatabasePokemon?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemons: DatabasePokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<DatabasePokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemons: DatabasePokemon)
}


