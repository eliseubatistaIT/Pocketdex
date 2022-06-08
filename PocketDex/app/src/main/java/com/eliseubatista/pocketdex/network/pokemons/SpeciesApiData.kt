package com.eliseubatista.pocketdex.network.pokemons

import android.util.Log
import com.eliseubatista.pocketdex.network.BaseNameAndUrl
import com.eliseubatista.pocketdex.network.PokeApi
import com.squareup.moshi.Json

/*
This is the detailed data of each pokemon specie
 */

data class SpeciesData(
    @Json(name = "color") val color: BaseNameAndUrl,
    @Json(name = "evolution_chain") val evolutionChain: SpeciesEvolutionChainData,
    @Json(name = "flavor_text_entries") val flavorEntries: List<SpeciesFlavorEntriesData>,
    @Json(name = "genera") val genera: List<SpeciesGeneraData>,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)

data class SpeciesEvolutionChainData(
    @Json(name = "url") val url: String
)

data class SpeciesFlavorEntriesData(
    @Json(name = "flavor_text") val flavorText: String
)

data class SpeciesGeneraData(
    @Json(name = "genus") val genus: String,
    @Json(name = "language") val language: BaseNameAndUrl
)

fun SpeciesData.getFlavor(): String {
    var flavor = ""

    if (this.flavorEntries.isNotEmpty()) {
        flavor = this.flavorEntries[0].flavorText
    }

    return flavor
}

fun SpeciesData.getGenus(): String {
    var genus = ""

    for (generaElement in this.genera) {
        if (generaElement.language.name == "en") {
            genus = generaElement.genus
        }
    }

    return genus
}