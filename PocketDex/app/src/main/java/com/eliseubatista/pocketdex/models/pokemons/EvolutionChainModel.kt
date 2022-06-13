package com.eliseubatista.pocketdex.models.pokemons

class EvolutionChainModel {
    var chainString = ""
    var evolutions = mutableListOf<PokemonModel>()

    constructor(chainString : String)
    {
        this.chainString = chainString
    }


}