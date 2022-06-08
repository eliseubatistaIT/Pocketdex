package com.eliseubatista.pocketdex.models.pokemons

import android.util.Log
import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.pokemons.PokemonStatData
import com.eliseubatista.pocketdex.network.pokemons.PokemonTypeData
import com.eliseubatista.pocketdex.network.pokemons.SpeciesFlavorEntriesData
import com.eliseubatista.pocketdex.network.pokemons.SpeciesGeneraData
import com.eliseubatista.pocketdex.repository.PocketdexRepository

class PokemonModel {

    var id = 0
    var height = 0
    var name = ""
    var color = ""
    var evolutionChain = listOf<String>()
    var flavor = ""
    var genus = ""
    var maleSprite = ""
    var femaleSprite = ""
    var hp = 0
    var attack = 0
    var defense = 0
    var specialAttack = 0
    var specialDefense = 0
    var speed = 0
    var types = listOf<String>()
    var weight = 0

    constructor(
        id: Int,
        height: Int,
        name: String,
        color: String,
        evolutionChain: List<String>,
        flavor: String,
        genus: String,
        maleSprite: String,
        femaleSprite: String,
        hp: Int,
        attack: Int,
        defense: Int,
        specialAttack: Int,
        specialDefense: Int,
        speed: Int,
        types: List<String>,
        weight: Int
    ) {
        this.id = id
        this.height = height
        this.name = name
        this.color = color
        this.evolutionChain = evolutionChain
        this.flavor = flavor
        this.genus = genus
        this.maleSprite = maleSprite
        this.femaleSprite = femaleSprite
        this.hp = hp
        this.attack = attack
        this.defense = defense
        this.specialAttack = specialAttack
        this.specialDefense = specialDefense
        this.speed = speed
        this.types = types
        this.weight = weight
    }

    override fun toString(): String {
        return "\nPokemon: ${id}, ${name}" +
                "\nEvolution Chain: ${evolutionChain}" +
                "\nTypes: ${types}" +
                "\nGenus: ${genus}\n"
    }

    companion object {

        fun fromDatabasePokemon(databasePokemon: DatabasePokemon): PokemonModel {

            return PokemonModel(
                databasePokemon.id,
                databasePokemon.height,
                databasePokemon.name,
                databasePokemon.color,
                databasePokemon.evolutionChain,
                databasePokemon.flavor,
                databasePokemon.genus,
                databasePokemon.maleSpriteUrl,
                databasePokemon.femaleSpriteUrl,
                databasePokemon.hp,
                databasePokemon.attack,
                databasePokemon.defense,
                databasePokemon.specialAttack,
                databasePokemon.specialDefense,
                databasePokemon.speed,
                databasePokemon.types,
                databasePokemon.weight
            )
        }

        suspend fun getPokemonData(
            pokemonName: String,
            pocketdexRepository: PocketdexRepository
        ): PokemonModel? {
            var pokemon: PokemonModel? = null

            try {
                //Try to get a pokemon data, and we succeed, return the data
                val pokemonDetailedData =
                    PokeApi.retrofitService.getPokemonByName(pokemonName)
                val pokemonStats = getPokemonStats(pokemonDetailedData.stats)
                //val pokemonTypes = getPokemonTypes(pokemonDetailedData.types, pocketdexRepository)
                val pokemonTypes = getPokemonTypes(pokemonDetailedData.types)

                val speciesData =
                    PokeApi.retrofitService.getSpeciesByName(pokemonDetailedData.species.name)
                val splitUrl = speciesData.evolutionChain.url.split("/")
                val chainID = splitUrl[splitUrl.size - 2]

                val evolutionChain = getEvolutionChain(chainID)

                val flavor = getFlavor(speciesData.flavorEntries)

                val genus = getGenus(speciesData.genera)

                val pokemonMaleSprite = pokemonDetailedData.sprites.frontDefault ?: ""
                val pokemonFemaleSprite = pokemonDetailedData.sprites.frontFemale ?: ""

                pokemon = PokemonModel(
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
            } catch (e: Exception) {
                //Otherwise, return null
                Log.i("ERROR FETCHING POKEMON", e.toString())
                pokemon = null
            }

            return pokemon
        }

        /*
        private suspend fun getPokemonTypes(
            pokemonTypesData: List<PokemonTypeData>,
            pocketdexRepository: PocketdexRepository
        ): List<TypeModel> {

            val pokemonTypesList = mutableListOf<TypeModel>()

            for (typeData in pokemonTypesData) {

                //val typeModel = TypeModel.getType(typeData.type.name)
                val typeModel = pocketdexRepository.getTypeByName(typeData.type.name)

                typeModel?.let { pokemonTypesList.add(it) }
            }

            return pokemonTypesList
        }
        */

        private suspend fun getPokemonTypes(
            pokemonTypesData: List<PokemonTypeData>
        ): List<String> {

            val pokemonTypesList = mutableListOf<String>()

            for (typeData in pokemonTypesData) {

                val type = typeData.type.name
                //val typeModel = pocketdexRepository.getTypeByName(typeData.type.name)

                pokemonTypesList.add(type)
                //typeModel?.let { pokemonTypesList.add(it) }
            }

            return pokemonTypesList
        }

        private fun getPokemonStats(pokemonStatsData: List<PokemonStatData>): List<Int> {
            val statsList = mutableListOf<Int>()

            for (stat in pokemonStatsData) {
                val statValue = stat.statValue

                statsList.add(statValue)
            }

            return statsList
        }

        private suspend fun getEvolutionChain(chainID: String): MutableList<String> {
            val evolutionChain = mutableListOf<String>()

            try {
                val evolutionChainData = PokeApi.retrofitService.getEvolutionChainById(chainID)

                evolutionChain.add(evolutionChainData.chain?.baseForm!!.name)

                for (evolution in evolutionChainData.chain.evolvesTo) {
                    if (evolution == null) {
                        continue;
                    }

                    evolutionChain.add(evolution.baseForm.name)

                    for (evolutionOfEvolution in evolution.evolvesTo) {
                        if (evolutionOfEvolution == null) {
                            continue;
                        }

                        evolutionChain.add(evolutionOfEvolution.baseForm.name)
                    }
                }
            } catch (e: Exception) {
                //Otherwise, return null
                Log.i("ERROR FETCHING CHAIN", e.toString())
            }

            return evolutionChain
        }

        private fun getFlavor(flavorEntries: List<SpeciesFlavorEntriesData>): String {
            var flavor = ""

            if (flavorEntries.isNotEmpty()) {
                flavor = flavorEntries[0].flavorText
            }

            return flavor
        }

        private fun getGenus(generaList: List<SpeciesGeneraData>): String {
            var genus = ""

            for (genera in generaList) {
                if (genera.language.name == "en") {
                    genus = genera.genus
                }
            }

            return genus
        }
    }
}