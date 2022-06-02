package com.elideus.pocketdex.network

import com.squareup.moshi.Json

/*
This is the pokemon name, and the url to its detailed data
 */
data class BaseNameAndUrl(
    @Json(name = "name") val name: String,
    @Json(name = "url") val pokemonUrl: String,
)
