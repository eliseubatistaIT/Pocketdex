package com.eliseubatista.pocketdex.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel

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
    val maleSpriteUrl: String,
    val femaleSpriteUrl: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val types: List<String>,
    val weight: Int
)

fun List<DatabasePokemon>.asDomainModel(): List<PokemonModel> {
    val pokemons = mutableListOf<PokemonModel>()

    for (element in this) {
        val pokemon = PokemonModel.fromDatabasePokemon(element)
        pokemons.add(pokemon)
    }

    return pokemons
}

