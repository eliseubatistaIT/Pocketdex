package com.eliseubatista.pocketdex.models.pokemons

import com.eliseubatista.pocketdex.database.pokemons.DatabasePokemon

class EvolutionChainModel(var chainString: String) {
    var evolutions = mutableListOf<DatabasePokemon>()
}