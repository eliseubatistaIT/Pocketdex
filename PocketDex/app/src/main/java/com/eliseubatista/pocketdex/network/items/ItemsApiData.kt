package com.eliseubatista.pocketdex.network.items

import com.eliseubatista.pocketdex.network.BaseNameAndUrl
import com.squareup.moshi.Json

/*
This is the detailed data of each Item
 */

data class ItemData(
    @Json(name = "attributes") val attributes: List<BaseNameAndUrl>,
    @Json(name = "baby_trigger_for") val babyTriggerFor: Boolean,
    @Json(name = "category") val category: List<BaseNameAndUrl>,
    @Json(name = "cost") val cost: Int,
    @Json(name = "effect_entries") val effectEntries: List<ItemEffectData>,
    @Json(name = "flavor_text_entries") val flavorTextEntries: List<ItemFlavorEntriesData>,
    @Json(name = "fling_effect") val flingEffect: String?,
    @Json(name = "fling_power") val flingPower: String?,
    @Json(name = "game_indices") val gameIndices: List<ItemGameIndiceData>,
    @Json(name = "held_by_pokemon") val heldByPokemon: List<BaseNameAndUrl?>,
    @Json(name = "machines") val machines: List<BaseNameAndUrl?>,
    @Json(name = "name") val name: String,
    @Json(name = "names") val names: List<ItemNameData>,
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
    @Json(name = "version_group") val versionGroup: BaseNameAndUrl
)


/*
This is the item game indice data retrieved from the item detailed data
 */
data class ItemGameIndiceData(
    @Json(name = "game_index") val gameIndex: Int,
    @Json(name = "generation") val generation: BaseNameAndUrl,
)

/*
This is the item name data retrieved from the item detailed data
 */
data class ItemNameData(
    @Json(name = "language") val language: BaseNameAndUrl,
    @Json(name = "name") val name: String,
)

/*
This is the item sprite data retrieved from the item detailed data
 */
data class ItemSpriteData(
    @Json(name = "default") val default: String,
)