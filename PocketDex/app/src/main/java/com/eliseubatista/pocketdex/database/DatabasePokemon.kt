package com.eliseubatista.pocketdex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabasePokemon constructor(
    @PrimaryKey
    val id: Int,
    val height: Int,
    val name: String,
    val color: String,
    val evolutionChain: List<String>,
    val flavor: String,
    val genus: String,
    val spriteUrl: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val types: List<String>,
    val weight: Int
){
    override fun toString(): String {
        return "\nPokemon: ${id}, $name" +
                "\nEvolution Chain: $evolutionChain" +
                "\nTypes: $types" +
                "\nGenus: ${genus}\n"
    }
}
