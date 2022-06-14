package com.eliseubatista.pocketdex.network.pokemons

import com.eliseubatista.pocketdex.network.BaseNameAndUrl
import com.squareup.moshi.Json

/*
This is the detailed data of each pokemon type
 */

data class TypeData(
    @Json(name = "damage_relations") val damageRelations: TypeDamageRelationsData,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
)

/*
This is the type damage relations data retrieved from the type detailed data
 */
data class TypeDamageRelationsData(
    @Json(name = "double_damage_from") val doubleDamageFrom: List<BaseNameAndUrl>,
    @Json(name = "double_damage_to") val doubleDamageTo: List<BaseNameAndUrl>,
    @Json(name = "half_damage_from") val halfDamageFrom: List<BaseNameAndUrl>,
    @Json(name = "half_damage_to") val halfDamageTo: List<BaseNameAndUrl>,
    @Json(name = "no_damage_from") val noDamageFrom: List<BaseNameAndUrl>,
    @Json(name = "no_damage_to") val noDamageTo: List<BaseNameAndUrl>,
)