package com.eliseubatista.pocketdex.models.pokemons

import android.util.Log
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.getNames

class TypeModel {

    var id = 0
    var doubleDamageFrom = listOf<String>()
    var doubleDamageTo = listOf<String>()
    var halfDamageFrom = listOf<String>()
    var halfDamageTo = listOf<String>()
    var noDamageFrom = listOf<String>()
    var noDamageTo = listOf<String>()
    var name = ""

    constructor(
        id: Int,
        doubleDamageFrom: List<String>,
        doubleDamageTo: List<String>,
        halfDamageFrom: List<String>,
        halfDamageTo: List<String>,
        noDamageFrom: List<String>,
        noDamageTo: List<String>,
        name: String
    ) {
        this.id = id
        this.doubleDamageFrom = doubleDamageFrom
        this.doubleDamageTo = doubleDamageTo
        this.halfDamageFrom = halfDamageFrom
        this.halfDamageTo = halfDamageTo
        this.noDamageFrom = noDamageFrom
        this.noDamageTo = noDamageTo
        this.name = name
    }

    override fun toString(): String {
        return "\nType: ${id}, ${name}" +
                "\nDouble Damage From: ${doubleDamageFrom}" +
                "\nDouble Damage To: ${doubleDamageTo}" +
                "\nHalf Damage From: ${halfDamageFrom}" +
                "\nHalf Damage To: ${halfDamageTo}" +
                "\nNo Damage From: ${noDamageFrom}" +
                "\nNo Damage To: ${noDamageTo}\n"
    }

    companion object {

        fun fromDatabaseType(databaseType: DatabaseTypes): TypeModel {

            return TypeModel(
                databaseType.id,
                databaseType.doubleDamageFrom,
                databaseType.doubleDamageTo,
                databaseType.halfDamageFrom,
                databaseType.halfDamageTo,
                databaseType.noDamageFrom,
                databaseType.noDamageTo,
                databaseType.name
            )
        }
    }
}