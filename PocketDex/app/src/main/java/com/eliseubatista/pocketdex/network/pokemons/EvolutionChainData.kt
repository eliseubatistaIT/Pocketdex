package com.eliseubatista.pocketdex.network.pokemons

import android.util.Log
import com.eliseubatista.pocketdex.network.BaseNameAndUrl
import com.squareup.moshi.Json

/*
This is the data of each evolution chain
 */

data class EvolutionChainData(
    @Json(name = "chain") val chain: EvolutionChainChainData?,
    @Json(name = "id") val id: Int,
)

data class EvolutionChainChainData(
    @Json(name = "evolves_to") val evolvesTo: List<EvolutionChainChainData?>,
    @Json(name = "species") val baseForm: BaseNameAndUrl,
)

fun EvolutionChainData.getEvolutionChain(): MutableList<String> {
    val evolutionChain = mutableListOf<String>()

    val baseForm = this.chain?.baseForm!!.name
    //evolutionChain.add(this.chain?.baseForm!!.name)

    var chainString = ""

    for (evolution in this.chain.evolvesTo) {
        if (evolution == null) {
            continue;
        }

        chainString = baseForm + getEvolutionChainString(evolution)

        evolutionChain.add(chainString)
    }

    Log.i("CHAIN", evolutionChain.toString())


    return evolutionChain
}

private fun getEvolutionChainString(evolutionChain: EvolutionChainChainData): String {

    var chainString = ":" + evolutionChain.baseForm.name

    for (evolution in evolutionChain.evolvesTo) {
        if (evolution == null) {
            continue;
        }

        chainString += ":" + evolution.baseForm.name

        for (evolutionOfEvolution in evolution.evolvesTo) {
            if (evolutionOfEvolution == null) {
                continue;
            }

            chainString += ":" + evolutionOfEvolution.baseForm.name
        }
    }

    return chainString
}
