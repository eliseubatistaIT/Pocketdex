package com.elideus.pocketdex.network

import com.squareup.moshi.Json

/*
This is the global ablity data
 */
data class AbilityGlobalData(
    @Json(name = "count") val numberOfPokemons: Int,
    @Json(name = "next") val next: String,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<BaseNameAndUrl>
)

