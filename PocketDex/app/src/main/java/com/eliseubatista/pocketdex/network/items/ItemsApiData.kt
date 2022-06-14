package com.eliseubatista.pocketdex.network.items

import com.eliseubatista.pocketdex.network.BaseNameAndUrl
import com.squareup.moshi.Json

/*
This is the detailed data of each Item
 */

data class ItemData(
    @Json(name = "attributes") val attributes: List<BaseNameAndUrl>,
    @Json(name = "category") val category: BaseNameAndUrl,
    @Json(name = "cost") val cost: Int,
    @Json(name = "effect_entries") val effectEntries: List<ItemEffectData>,
    @Json(name = "flavor_text_entries") val flavorTextEntries: List<ItemFlavorEntriesData>,
    @Json(name = "name") val name: String,
    @Json(name = "sprites") val sprites: ItemSpriteData,
)

/*
This is the item effect data retrieved from the item detailed data
 */
data class ItemEffectData(
    @Json(name = "effect") val effect: String,
    @Json(name = "language") val language: BaseNameAndUrl,
    @Json(name = "short_effect") val shortEffect: String
)


/*
This is the item flavor entries data retrieved from the item detailed data
 */
data class ItemFlavorEntriesData(
    @Json(name = "language") val language: BaseNameAndUrl,
    @Json(name = "text") val text: String,
)

/*
This is the item sprite data retrieved from the item detailed data
 */
data class ItemSpriteData(
    @Json(name = "default") val default: String,
)