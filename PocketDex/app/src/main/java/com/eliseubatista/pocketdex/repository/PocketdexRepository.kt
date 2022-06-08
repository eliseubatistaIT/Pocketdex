package com.eliseubatista.pocketdex.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.database.PocketdexDatabase
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.utils.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PocketdexRepository(private val database: PocketdexDatabase) {

    val pokemonTypes: LiveData<List<TypeModel>> =
        Transformations.map(database.typesDao.getTypes()) {
            it.asDomainModel()
        }

    val pokemons: LiveData<List<PokemonModel>> =
        Transformations.map(database.pokemonDao.getPokemons()) {
            it.asDomainModel()
        }

    suspend fun refreshTypes(application: Application) {
        if (!hasInternetConnection(application)) {
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

    suspend fun refreshPokemons(application: Application, limit: Int, offset: Int) {
        if (!hasInternetConnection(application)) {
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

    suspend fun getTypeByName(application: Application, name: String): TypeModel? {

        if (!hasInternetConnection(application)) {
            return null
        }

        var typeModel: TypeModel? = null

        withContext(Dispatchers.IO) {

            var typeDatabase = database.typesDao.getTypeByName(name)

            if (typeDatabase == null) {
                val typeDetailedData = PokeApi.retrofitService.getTypeByName(name)

                //pokeDatabase = PokemonApiToPokemonDatabase(pokemonDetailedData)
                typeDatabase = typeDetailedData.asDatabaseModel()
            }

            typeModel = typeDatabase.asDomainModel()
        }

        return typeModel

    }

    suspend fun getPokemonByName(application: Application, name: String): PokemonModel? {
        if (!hasInternetConnection(application)) {
            return null
        }
        var pokeModel: PokemonModel? = null

        withContext(Dispatchers.IO) {

            var pokeDatabase = database.pokemonDao.getPokemonByName(name)

            if (pokeDatabase == null) {
                val pokemonDetailedData = PokeApi.retrofitService.getPokemonByName(name)

                pokeDatabase = pokemonDetailedData.asDatabaseModel()
            }

            pokeModel = pokeDatabase.asDomainModel()
        }

        return pokeModel

    }
}