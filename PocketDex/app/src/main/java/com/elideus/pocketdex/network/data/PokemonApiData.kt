package com.elideus.pocketdex.network

import com.squareup.moshi.Json


/*
This is the global pokemon data
 */
data class PokemonsGlobalData(
    @Json(name = "count") val numberOfPokemons: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<BaseNameAndUrl>
)

/*
This is the detailed data of each pokemon
 */

data class PokemonDetailedData(
    @Json(name = "abilities") val abilities: List<PokemonAbilityData>,
    @Json(name = "base_experience") val baseExperience: Int,
    @Json(name = "forms") val forms: List<BaseNameAndUrl>,
    @Json(name = "game_indices") val gameIndices: List<PokemonGameIndiceData>,
    @Json(name = "height") val height: Int,
    @Json(name = "held_items") val heldItems: List<PokemonHeldItemsData>,
    @Json(name = "id") val id: Int,
    @Json(name = "is_default") val isDefault: Boolean,
    @Json(name = "location_area_encounters") val locationAreaEncountersUrl: String,
    @Json(name = "moves") val moves: List<PokemonMovesData>,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    //@Json(name="past_types") val pastTypes: List<PokemonPastTypeData>,
    @Json(name = "species") val species: BaseNameAndUrl,
    @Json(name = "sprites") val sprites: PokemonSpritesData,
    @Json(name = "stats") val stats: List<PokemonStatData>,
    @Json(name = "types") val types: List<PokemonTypeData>,
    @Json(name = "weight") val weight: Int,
)

/*
This is the ability data retrieved from the pokemon detailed data
 */
data class PokemonAbilityData(
    @Json(name = "ability") val ability: BaseNameAndUrl,
    @Json(name = "is_hidden") val isHidden: Boolean,
    @Json(name = "slot") val slot: Int
)

/*
This is the game indices data retrieved from the pokemon detailed data
 */
data class PokemonGameIndiceData(
    @Json(name = "game_index") val gameIndex: Int,
    @Json(name = "version") val indiceVersion: BaseNameAndUrl,
)

/*
This is the held items data retrieved from the pokemon detailed data
 */
data class PokemonHeldItemsData(
    @Json(name = "item") val item: BaseNameAndUrl,
    @Json(name = "version_details") val versionDetail: List<PokemonHeldItemVersionDetailsData>,
)

/*
This is the held items version details data retrieved from the pokemon detailed data
 */
data class PokemonHeldItemVersionDetailsData(
    @Json(name = "rarity") val rarity: Int,
    @Json(name = "version") val version: BaseNameAndUrl,
)

/*
This is the pokemon moves data retrieved from the pokemon detailed data
 */
data class PokemonMovesData(
    @Json(name = "move") val move: BaseNameAndUrl,
    @Json(name = "version_group_details") val versionGroupDetails: List<PokemonMoveVersionGroup>,
)

/*
This is the pokemon moves data retrieved from the pokemon detailed data
 */
data class PokemonMoveVersionGroup(
    @Json(name = "level_learned_at") val levelLearnedAt: Int,
    @Json(name = "move_learn_method") val moveLearnMethod: BaseNameAndUrl,
    @Json(name = "version_group") val versionGroup: BaseNameAndUrl,
)

/*
This is the pokemon moves data retrieved from the pokemon detailed data
 */
data class PokemonSpritesData(
    @Json(name = "back_default") val backDefault: String?,
    @Json(name = "back_female") val backFemale: String?,
    @Json(name = "back_shiny") val backShiny: String?,
    @Json(name = "back_shiny_female") val backShinyFemale: String?,
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_female") val frontFemale: String?,
    @Json(name = "front_shiny") val frontShiny: String?,
    @Json(name = "front_shiny_female") val frontShinyFemale: String?,
    //@Json(name = "other") val other: PokemonOtherData,
    //@Json(name = "versions") val other: PokemonSpritesVersionsData,
)

/*
This is the pokemon stat data retrieved from the pokemon detailed data
 */
data class PokemonStatData(
    @Json(name = "base_stat") val baseStat: Int,
    @Json(name = "effort") val effort: Int,
    @Json(name = "stat") val stat: BaseNameAndUrl,
)

/*
This is the pokemon type data retrieved from the pokemon detailed data
 */
data class PokemonTypeData(
    @Json(name = "slot") val slot: Int,
    @Json(name = "type") val type: BaseNameAndUrl,
)



