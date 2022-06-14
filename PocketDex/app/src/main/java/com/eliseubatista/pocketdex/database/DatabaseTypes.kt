package com.eliseubatista.pocketdex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

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
) {
    override fun toString(): String {
        return "\nType: ${id}, $name" +
                "\nDouble Damage From: $doubleDamageFrom" +
                "\nDouble Damage To: $doubleDamageTo" +
                "\nHalf Damage From: $halfDamageFrom" +
                "\nHalf Damage To: $halfDamageTo" +
                "\nNo Damage From: $noDamageFrom" +
                "\nNo Damage To: ${noDamageTo}\n"
    }
}
