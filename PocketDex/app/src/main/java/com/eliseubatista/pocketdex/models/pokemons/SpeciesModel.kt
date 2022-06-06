package com.eliseubatista.pocketdex.models.pokemons

import android.util.Log
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.pokemons.SpeciesFlavorEntriesData
import com.eliseubatista.pocketdex.network.pokemons.SpeciesGeneraData

class SpeciesModel {
    var id = 0
    var name = ""
    var color = ""
    var evolutionChain = listOf<String>()
    var flavor = ""
    var genus = ""

    constructor(
        id: Int,
        name: String,
        color: String,
    ) {
        this.id = id
        this.name = name
        this.color = color
    }

    constructor(
        id: Int,
        name: String,
        color: String,
        evolutionChain: List<String>,
        flavor: String,
        genus: String
    ) {
        this.id = id
        this.name = name
        this.color = color
        this.evolutionChain = evolutionChain
        this.flavor = flavor
        this.genus = genus
    }

    companion object {
        suspend fun getSpecies(specieName: String): SpeciesModel? {
            var specieModel: SpeciesModel? = null

            try {
                //Try to get the type data, and if we succeed, return the data
                val speciesData = PokeApi.retrofitService.getSpeciesByName(specieName)

                val splitUrl = speciesData.evolutionChain.url.split("/")

                val chainID = splitUrl[splitUrl.size - 2]

                val evolutionChain = getEvolutionChain(chainID)

                val flavor = getFlavor(speciesData.flavorEntries)

                val genus = getGenus(speciesData.genera)

                specieModel = SpeciesModel(
                    speciesData.id,
                    speciesData.name,
                    speciesData.color.name,
                    evolutionChain,
                    flavor,
                    genus
                )
            } catch (e: Exception) {
                //Otherwise, return null
                Log.i("ERROR FETCHING SPECIE", e.toString())
                specieModel = null
            }

            //Log.i("FETCHING TYPE", specieModel?.name.toString())

            return specieModel
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