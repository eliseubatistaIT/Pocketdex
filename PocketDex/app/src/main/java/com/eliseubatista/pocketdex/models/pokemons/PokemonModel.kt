package com.eliseubatista.pocketdex.models.pokemons

import android.util.Log
import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.pokemons.PokemonStatData
import com.eliseubatista.pocketdex.network.pokemons.PokemonTypeData
import com.eliseubatista.pocketdex.network.pokemons.SpeciesFlavorEntriesData
import com.eliseubatista.pocketdex.network.pokemons.SpeciesGeneraData
import com.eliseubatista.pocketdex.repository.PocketdexRepository

class PokemonModel {

    var id = 0
    var height = 0
    var name = ""
    var color = ""
    var evolutionChain = listOf<String>()
    var flavor = ""
    var genus = ""
    var maleSprite = ""
    var femaleSprite = ""
    var hp = 0
    var attack = 0
    var defense = 0
    var specialAttack = 0
    var specialDefense = 0
    var speed = 0
    var types = listOf<String>()
    var weight = 0

    constructor(
        id: Int,
        height: Int,
        name: String,
        color: String,
        evolutionChain: List<String>,
        flavor: String,
        genus: String,
        maleSprite: String,
        femaleSprite: String,
        hp: Int,
        attack: Int,
        defense: Int,
        specialAttack: Int,
        specialDefense: Int,
        speed: Int,
        types: List<String>,
        weight: Int
    ) {
        this.id = id
        this.height = height
        this.name = name
        this.color = color
        this.evolutionChain = evolutionChain
        this.flavor = flavor
        this.genus = genus
        this.maleSprite = maleSprite
        this.femaleSprite = femaleSprite
        this.hp = hp
        this.attack = attack
        this.defense = defense
        this.specialAttack = specialAttack
        this.specialDefense = specialDefense
        this.speed = speed
        this.types = types
        this.weight = weight
    }

    override fun toString(): String {
        return "\nPokemon: ${id}, ${name}" +
                "\nEvolution Chain: ${evolutionChain}" +
                "\nTypes: ${types}" +
                "\nGenus: ${genus}\n"
    }

    companion object {

        fun fromDatabasePokemon(databasePokemon: DatabasePokemon): PokemonModel {

            return PokemonModel(
                databasePokemon.id,
                databasePokemon.height,
                databasePokemon.name,
                databasePokemon.color,
                databasePokemon.evolutionChain,
                databasePokemon.flavor,
                databasePokemon.genus,
                databasePokemon.maleSpriteUrl,
                databasePokemon.femaleSpriteUrl,
                databasePokemon.hp,
                databasePokemon.attack,
                databasePokemon.defense,
                databasePokemon.specialAttack,
                databasePokemon.specialDefense,
                databasePokemon.speed,
                databasePokemon.types,
                databasePokemon.weight
            )
        }
    }
}