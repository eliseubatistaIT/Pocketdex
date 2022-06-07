package com.eliseubatista.pocketdex.network

import com.squareup.moshi.Json

/*
This is the pokemon name, and the url to its detailed data
 */
data class BaseNameAndUrl(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
)

/*
This is the global pokemon data
 */
data class GlobalSearchData(
    @Json(name = "count") val numberOfResults: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<BaseNameAndUrl>
)

fun List<BaseNameAndUrl>.getNames(): List<String> {
    val names = mutableListOf<String>()

    for (element in this) {
        names.add(element.name)
    }

    return names
}

fun List<BaseNameAndUrl>.getUrls(): List<String> {
    val urls = mutableListOf<String>()

    for (element in this) {
        urls.add(element.url)
    }

    return urls
}

fun GlobalSearchData.getNames(): List<String> {
    val globalNames = mutableListOf<String>()

    for (result in results) {
        globalNames.add(result.name)
    }

    return globalNames
}

fun GlobalSearchData.getUrls(): List<String> {
    val globalUrls = mutableListOf<String>()

    for (result in results) {
        globalUrls.add(result.url)
    }

    return globalUrls
}