package com.elideus.pocketdex.network.data

import com.elideus.pocketdex.network.BaseNameAndUrl
import com.elideus.pocketdex.network.PokemonHeldItemsData
import com.elideus.pocketdex.network.PokemonTypeData
import com.squareup.moshi.Json

/*
This is the detailed data of each pokemon type
 */

data class TypeDetailedData(
    @Json(name = "damage_relations") val damageRelations: TypeDamageRelationsData,
    @Json(name = "game_indices") val gameIndices: List<TypeGameIndiceData>,
    @Json(name = "generation") val generation: BaseNameAndUrl,
    @Json(name = "id") val id: Int,
    @Json(name = "move_damage_class") val moveDamageClass: BaseNameAndUrl?,
    @Json(name = "moves") val moves: List<BaseNameAndUrl>,
    @Json(name = "name") val name: String,
    @Json(name = "names") val names: List<TypeNameData>,
    //@Json(name = "past_damage_relations") val pastDamageRelations: List<TypeNameData>,
    @Json(name = "pokemon") val pokemons: List<TypePokemonData>,


    //-----------------------------------------------------------------------------------------------
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

/*
This is the type game indice data retrieved from the type detailed data
 */
data class TypeGameIndiceData(
    @Json(name = "game_index") val gameIndex: Int,
    @Json(name = "generation") val generation: BaseNameAndUrl,
)

/*
This is the type name data retrieved from the type detailed data
 */
data class TypeNameData(
    @Json(name = "language") val language: BaseNameAndUrl,
    @Json(name = "name") val name: String,
)

/*
This is the type pokemon data retrieved from the type detailed data
 */
data class TypePokemonData(
    @Json(name = "pokemon") val pokemon: BaseNameAndUrl,
    @Json(name = "slot") val slot: Int,
)