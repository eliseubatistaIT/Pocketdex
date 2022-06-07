package com.eliseubatista.pocketdex.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eliseubatista.pocketdex.models.pokemons.SpeciesModel
import com.eliseubatista.pocketdex.models.pokemons.StatModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.network.getNames

@Entity
data class DatabasePokemon constructor(
    @PrimaryKey
    val id: Int,
    val height: Int,
    val name: String,
    val maleSpriteUrl: String,
    val femaleSpriteUrl: String,
    val stats: DatabasePokemonStatsModels,
    val types: List<Int>,
    val weight: Int
)

