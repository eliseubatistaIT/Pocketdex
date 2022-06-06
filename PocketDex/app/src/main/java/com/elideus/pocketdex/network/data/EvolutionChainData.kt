package com.elideus.pocketdex.network.data

import com.elideus.pocketdex.network.BaseNameAndUrl
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

