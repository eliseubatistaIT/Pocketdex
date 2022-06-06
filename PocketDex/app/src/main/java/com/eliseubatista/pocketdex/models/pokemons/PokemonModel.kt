package com.eliseubatista.pocketdex.models.pokemons

import android.util.Log
import com.eliseubatista.pocketdex.models.pokemons.StatModel.Companion.getStat
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.pokemons.PokemonStatData
import com.eliseubatista.pocketdex.network.pokemons.PokemonTypeData

class PokemonModel {

    var id = 0
    var height = 0
    var name = ""
    var species: SpeciesModel
    var maleSprite = ""
    var femaleSprite = ""
    var stats = listOf<StatModel>()
    var types = listOf<TypeModel>()
    var weight = 0

    constructor(
        id: Int,
        height: Int,
        name: String,
        species: SpeciesModel,
        maleSprite: String,
        femaleSprite: String,
        stats: List<StatModel>,
        types: List<TypeModel>,
        weight: Int
    ) {
        this.id = id
        this.height = height
        this.name = name
        this.species = species
        this.maleSprite = maleSprite
        this.femaleSprite = femaleSprite
        this.stats = stats
        this.types = types
        this.weight = weight
    }

    companion object {
        suspend fun getPokemonData(pokemonName: String): PokemonModel? {
            var pokemon: PokemonModel? = null

            try {
                //Try to get a pokemon data, and we succeed, return the data
                val pokemonDetailedData =
                    PokeApi.retrofitService.getPokemonByName(pokemonName)
                val pokemonStats = getPokemonStats(pokemonDetailedData.stats)
                val pokemonTypes = getPokemonTypes(pokemonDetailedData.types)
                val pokemonSpecies = SpeciesModel.getSpecies(pokemonDetailedData.species.name)
                val pokemonMaleSprite = pokemonDetailedData.sprites.frontDefault ?: ""
                val pokemonFemaleSprite = pokemonDetailedData.sprites.frontFemale ?: ""

                pokemon = PokemonModel(
                    pokemonDetailedData.id,
                    pokemonDetailedData.height,
                    pokemonDetailedData.name,
                    pokemonSpecies!!,
                    pokemonMaleSprite,
                    pokemonFemaleSprite,
                    pokemonStats,
                    pokemonTypes,
                    pokemonDetailedData.weight
                )
            } catch (e: Exception) {
                //Otherwise, return null
                Log.i("ERROR FETCHING POKEMON", e.toString())
                pokemon = null
            }

            return pokemon
        }

        private suspend fun getPokemonTypes(pokemonTypesData: List<PokemonTypeData>): List<TypeModel> {

            val pokemonTypesList = mutableListOf<TypeModel>()

            for (typeData in pokemonTypesData) {

                val typeModel = TypeModel.getType(typeData.type.name)

                typeModel?.let { pokemonTypesList.add(it) }
            }

            return pokemonTypesList
        }

        private fun getPokemonStats(pokemonStatsData: List<PokemonStatData>): List<StatModel> {
            val statsList = mutableListOf<StatModel>()

            for (stat in pokemonStatsData) {
                val statModel = getStat(stat.stat.name, stat.statValue)

                statsList.add(statModel)
            }

            return statsList
        }
    }
}