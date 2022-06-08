package com.eliseubatista.pocketdex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.database.PocketdexDatabase
import com.eliseubatista.pocketdex.database.asDomainModel
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.pokemons.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PocketdexRepository(private val database: PocketdexDatabase) {

    val pokemonTypes: LiveData<List<TypeModel>> =
        Transformations.map(database.typesDao.getTypes()) {
            it.asDomainModel()
        }

    suspend fun refreshTypes() {
        withContext(Dispatchers.IO) {
            val typesGlobalData = PokeApi.retrofitService.getTypes(100, 0)

            val typesDataList = mutableListOf<DatabaseTypes>()

            for (typeResult in typesGlobalData.results) {
                val typeData = PokeApi.retrofitService.getTypeByName(typeResult.name)
                typesDataList.add(typeData.asDatabaseModel())
            }

            //database.typesDao.insertAll(*typesDataList.asDatabaseModel())
            database.typesDao.insertAll(typesDataList)
        }
    }

    suspend fun refreshPokemons() {
        withContext(Dispatchers.IO) {
            val pokemonsGlobalData = PokeApi.retrofitService.getPokemons(20, 0)

            val pokemonsDataList = mutableListOf<DatabasePokemon>()

            for (pokemonResult in pokemonsGlobalData.results) {
                val pokemonDetailedData =
                    PokeApi.retrofitService.getPokemonByName(pokemonResult.name)
                val pokemonStats = pokemonDetailedData.getPokemonStats()
                val pokemonTypes = pokemonDetailedData.getPokemonTypes()
                val speciesData =
                    PokeApi.retrofitService.getSpeciesByName(pokemonDetailedData.species.name)

                //Split the url to get the chain id
                val splitUrl = speciesData.evolutionChain.url.split("/")
                val chainID = splitUrl[splitUrl.size - 2]

                val evolutionChainData = PokeApi.retrofitService.getEvolutionChainById(chainID)

                val evolutionChain = evolutionChainData.getEvolutionChain()

                val flavor = speciesData.getFlavor()

                val genus = speciesData.getGenus()

                val pokemonMaleSprite = pokemonDetailedData.sprites.frontDefault ?: ""
                val pokemonFemaleSprite = pokemonDetailedData.sprites.frontFemale ?: ""

                val databasePokemon = DatabasePokemon(
                    pokemonDetailedData.id,
                    pokemonDetailedData.height,
                    pokemonDetailedData.name,
                    speciesData.color.name,
                    evolutionChain,
                    flavor,
                    genus,
                    pokemonMaleSprite,
                    pokemonFemaleSprite,
                    pokemonStats[0],
                    pokemonStats[1],
                    pokemonStats[2],
                    pokemonStats[3],
                    pokemonStats[4],
                    pokemonStats[5],
                    pokemonTypes,
                    pokemonDetailedData.weight
                )

                pokemonsDataList.add(databasePokemon)
            }

            //database.typesDao.insertAll(*typesDataList.asDatabaseModel())
            database.pokemonDao.insertAll(pokemonsDataList)
        }
    }

    fun getTypeByName(name: String): TypeModel? {
        if (pokemonTypes == null) {
            Log.i("REPOSITORY", "Pokemon Types is Null")
            return null
        }

        if (pokemonTypes.value == null) {
            Log.i("REPOSITORY", "Pokemon Types Value is Null")
            return null
        }

        for (pokeType in pokemonTypes.value!!) {
            if (pokeType.name == name) {
                return pokeType
            }
        }

        return pokemonTypes.value!![0]
    }

}