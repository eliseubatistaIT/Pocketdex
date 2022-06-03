package com.elideus.pocketdex.models

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
}