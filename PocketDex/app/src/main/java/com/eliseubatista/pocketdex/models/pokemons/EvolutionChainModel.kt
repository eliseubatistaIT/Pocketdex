package com.eliseubatista.pocketdex.models.pokemons

import com.eliseubatista.pocketdex.database.DatabasePokemon

class EvolutionChainModel(var chainString: String) {
    var evolutions = mutableListOf<DatabasePokemon>()
}