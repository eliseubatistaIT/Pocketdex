package com.eliseubatista.pocketdex.network.pokemons

import android.util.Log
import com.eliseubatista.pocketdex.network.BaseNameAndUrl
import com.eliseubatista.pocketdex.network.PokeApi
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

    evolutionChain.add(this.chain?.baseForm!!.name)

    for (evolution in this.chain.evolvesTo) {
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

    return evolutionChain
}
