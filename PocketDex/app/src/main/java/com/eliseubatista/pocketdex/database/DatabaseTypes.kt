package com.eliseubatista.pocketdex.database

import android.util.Log
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
