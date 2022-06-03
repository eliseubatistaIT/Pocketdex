package com.elideus.pocketdex.network

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

fun getNamesFromNamesAndUrl(listOfNamesAndUrls: List<BaseNameAndUrl>): List<String> {
    val result = mutableListOf<String>()
    for (nameAndUrl in listOfNamesAndUrls) {
        result.add(nameAndUrl.name)
    }

    return result
}

fun getUrlFromNamesAndUrl(listOfNamesAndUrls: List<BaseNameAndUrl>): List<String> {
    val result = mutableListOf<String>()
    for (nameAndUrl in listOfNamesAndUrls) {
        result.add(nameAndUrl.url)
    }

    return result
}