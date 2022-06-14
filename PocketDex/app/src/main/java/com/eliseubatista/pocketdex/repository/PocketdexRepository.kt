package com.eliseubatista.pocketdex.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.eliseubatista.pocketdex.database.DatabaseFavorites
import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.database.PocketdexDatabase
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.utils.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PocketdexRepository(private val database: PocketdexDatabase) {

    val pokemonTypes: LiveData<List<DatabaseTypes>> = database.typesDao.getTypes()

    val pokemons: LiveData<List<DatabasePokemon>> = database.pokemonDao.getPokemons()

    val favoritePokemons: LiveData<List<DatabaseFavorites>> =
        database.favoritesDao.getFavoritePokemons()

    val favoriteItems: LiveData<List<DatabaseFavorites>> = database.favoritesDao.getFavoriteItems()

    val favoriteLocations: LiveData<List<DatabaseFavorites>> =
        database.favoritesDao.getFavoriteLocations()

    suspend fun addToFavorites(favorite: DatabaseFavorites) {
        withContext(Dispatchers.IO) {
            database.favoritesDao.insert(favorite)
        }
    }

    suspend fun removeFromFavorites(favorite: DatabaseFavorites?) {
        if (favorite == null) {
            return
        }

        withContext(Dispatchers.IO) {
            database.favoritesDao.delete(favorite)
        }
    }

    suspend fun getTypes(appContext: Context) {
        var types: MutableList<DatabaseTypes>

        withContext(Dispatchers.IO) {
            types = database.typesDao.getTypesInRange(0, 100).toMutableList()

            if (types.isEmpty() && hasInternetConnection(appContext)) {

                val typesGlobalData = PokeApi.retrofitService.getTypes(100, 0)

                for (typeResult in typesGlobalData.results) {
                    val typeData = PokeApi.retrofitService.getTypeByName(typeResult.name)
                    val typeDatabase = typeData.asDatabaseModel()
                    types.add(typeDatabase)
                }
            }

            database.typesDao.insertAll(types)
        }
    }

    suspend fun refreshTypes(appContext: Context) {
        if (!hasInternetConnection(appContext)) {
            return
        }
        withContext(Dispatchers.IO) {
            val typesGlobalData = PokeApi.retrofitService.getTypes(100, 0)

            val typesDataList = mutableListOf<DatabaseTypes>()

            for (typeResult in typesGlobalData.results) {
                val typeData = PokeApi.retrofitService.getTypeByName(typeResult.name)
                val typeDatabase = typeData.asDatabaseModel()
                typesDataList.add(typeDatabase)
            }

            database.typesDao.insertAll(typesDataList)
        }
    }

    suspend fun getPokemons(appContext: Context, limit: Int, offset: Int) {
        if (!hasInternetConnection(appContext)) {
            return
        }

        var pokemons: MutableList<DatabasePokemon>

        withContext(Dispatchers.IO) {
            val max = offset + limit
            pokemons = database.pokemonDao.getPokemonsInRange(offset, max).toMutableList()

            if (pokemons.isEmpty() && hasInternetConnection(appContext)) {

                val pokemonsGlobalData = PokeApi.retrofitService.getPokemons(limit, offset)

                for (pokemonResult in pokemonsGlobalData.results) {
                    val pokemonDetailedData =
                        PokeApi.retrofitService.getPokemonByName(pokemonResult.name)

                    val databasePokemon = pokemonDetailedData.asDatabaseModel()

                    pokemons.add(databasePokemon)
                }
            }

            database.pokemonDao.insertAll(pokemons)
        }
    }

    suspend fun refreshPokemons(appContext: Context, limit: Int, offset: Int) {
        if (!hasInternetConnection(appContext)) {
            return
        }
        withContext(Dispatchers.IO) {
            val pokemonsGlobalData = PokeApi.retrofitService.getPokemons(limit, offset)

            val pokemonsDataList = mutableListOf<DatabasePokemon>()

            for (pokemonResult in pokemonsGlobalData.results) {
                val pokemonDetailedData =
                    PokeApi.retrofitService.getPokemonByName(pokemonResult.name)

                val databasePokemon = pokemonDetailedData.asDatabaseModel()

                pokemonsDataList.add(databasePokemon)
            }

            database.pokemonDao.insertAll(pokemonsDataList)
        }
    }

    suspend fun getFavoriteByName(name: String): DatabaseFavorites? {

        var favorite: DatabaseFavorites?

        withContext(Dispatchers.IO) {

            favorite = database.favoritesDao.getFavoriteByName(name)
        }

        return favorite
    }

    suspend fun getTypeByName(appContext: Context, name: String): DatabaseTypes? {

        if (!hasInternetConnection(appContext)) {
            return null
        }

        var typeDatabase: DatabaseTypes?

        withContext(Dispatchers.IO) {

            typeDatabase = database.typesDao.getTypeByName(name)

            if (typeDatabase == null) {
                val typeDetailedData = PokeApi.retrofitService.getTypeByName(name)

                //pokeDatabase = PokemonApiToPokemonDatabase(pokemonDetailedData)
                typeDatabase = typeDetailedData.asDatabaseModel()
            }
        }

        return typeDatabase

    }

    suspend fun getPokemonByName(appContext: Context, name: String): DatabasePokemon? {
        if (!hasInternetConnection(appContext)) {
            return null
        }
        var pokeDatabase: DatabasePokemon?

        withContext(Dispatchers.IO) {

            pokeDatabase = database.pokemonDao.getPokemonByName(name)

            if (pokeDatabase == null) {
                val pokemonDetailedData = PokeApi.retrofitService.getPokemonByName(name)

                pokeDatabase = pokemonDetailedData.asDatabaseModel()
            }
        }

        return pokeDatabase
    }
}