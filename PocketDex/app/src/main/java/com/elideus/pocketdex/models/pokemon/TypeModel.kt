package com.elideus.pocketdex.models.pokemon

import android.util.Log
import com.elideus.pocketdex.network.PokeApi
import com.elideus.pocketdex.network.getNamesFromNamesAndUrl

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

    companion object {
        suspend fun getType(typeName: String): TypeModel? {
            var typeModel: TypeModel? = null

            try {
                //Try to get the type data, and if we succeed, return the data
                val typeDetailedData = PokeApi.retrofitService.getTypeByName(typeName)

                val doubleDamageFrom =
                    getNamesFromNamesAndUrl(typeDetailedData.damageRelations.doubleDamageFrom)
                val doubleDamageTo =
                    getNamesFromNamesAndUrl(typeDetailedData.damageRelations.doubleDamageTo)
                val halfDamageFrom =
                    getNamesFromNamesAndUrl(typeDetailedData.damageRelations.halfDamageFrom)
                val halfDamageTo =
                    getNamesFromNamesAndUrl(typeDetailedData.damageRelations.halfDamageTo)
                val noDamageFrom =
                    getNamesFromNamesAndUrl(typeDetailedData.damageRelations.noDamageFrom)
                val noDamageTo =
                    getNamesFromNamesAndUrl(typeDetailedData.damageRelations.noDamageTo)

                typeModel = TypeModel(
                    typeDetailedData.id,
                    doubleDamageFrom,
                    doubleDamageTo,
                    halfDamageFrom,
                    halfDamageTo,
                    noDamageFrom,
                    noDamageTo,
                    typeDetailedData.name
                )
            } catch (e: Exception) {
                //Otherwise, return null
                Log.i("ERROR FETCHING TYPE", e.toString())
                typeModel = null
            }

            return typeModel
        }
    }
}