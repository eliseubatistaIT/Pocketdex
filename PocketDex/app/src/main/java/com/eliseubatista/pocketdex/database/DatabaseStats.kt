package com.eliseubatista.pocketdex.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eliseubatista.pocketdex.models.pokemons.StatModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel

@Entity
data class DatabaseStats constructor(
    @PrimaryKey
    val name: String,
    val value: Int,
)

class DatabasePokemonStatsModels {
    var pokemonStats = listOf<StatModel>()
}