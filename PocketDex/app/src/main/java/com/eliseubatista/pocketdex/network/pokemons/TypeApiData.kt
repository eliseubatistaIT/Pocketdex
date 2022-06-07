package com.eliseubatista.pocketdex.network.pokemons

import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.network.BaseNameAndUrl
import com.eliseubatista.pocketdex.network.getNames
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

fun List<TypeData>.asDatabaseTypeData(): Array<DatabaseTypes> {

    val listOfDatabaseTypes = mutableListOf<DatabaseTypes>()

    for (typeData in this) {
        val _doubleDamageFrom = typeData.damageRelations.doubleDamageFrom.getNames()
        val _doubleDamageTo = typeData.damageRelations.doubleDamageTo.getNames()
        val _halfDamageFrom = typeData.damageRelations.halfDamageFrom.getNames()
        val _halfDamageTo = typeData.damageRelations.halfDamageTo.getNames()
        val _noDamageFrom = typeData.damageRelations.noDamageFrom.getNames()
        val _noDamageTo = typeData.damageRelations.noDamageTo.getNames()

        val databaseType = DatabaseTypes(
            typeData.id,
            _doubleDamageFrom,
            _doubleDamageTo,
            _halfDamageFrom,
            _halfDamageTo,
            _noDamageFrom,
            _noDamageTo,
            typeData.name
        )

        listOfDatabaseTypes.add(databaseType)
    }

    return listOfDatabaseTypes.toTypedArray()
}