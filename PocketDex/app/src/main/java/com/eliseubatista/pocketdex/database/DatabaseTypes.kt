package com.eliseubatista.pocketdex.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eliseubatista.pocketdex.models.pokemons.TypeModel

@Entity
data class DatabaseTypes constructor(
    @PrimaryKey
    val id: Int,
    val doubleDamageFrom: List<String>,
    val doubleDamageTo: List<String>,
    val halfDamageFrom: List<String>,
    val halfDamageTo: List<String>,
    val noDamageFrom: List<String>,
    val noDamageTo: List<String>,
    val name: String
)

fun List<DatabaseTypes>.asTypeModel() : List<TypeModel>{
    val types = mutableListOf<TypeModel>()

    for (element in this) {
        //val type = TypeModel.
        //names.add(element.name)
    }

    return types
}

