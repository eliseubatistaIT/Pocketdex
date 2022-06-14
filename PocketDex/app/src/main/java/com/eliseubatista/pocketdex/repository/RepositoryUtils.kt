package com.eliseubatista.pocketdex.repository

import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.getNames
import com.eliseubatista.pocketdex.network.pokemons.*

@JvmName("asDatabaseTypeTypeData")
fun TypeData.asDatabaseModel(): DatabaseTypes {

    val doubleDamageFrom = this.damageRelations.doubleDamageFrom.getNames()
    val doubleDamageTo = this.damageRelations.doubleDamageTo.getNames()
    val halfDamageFrom = this.damageRelations.halfDamageFrom.getNames()
    val halfDamageTo = this.damageRelations.halfDamageTo.getNames()
    val noDamageFrom = this.damageRelations.noDamageFrom.getNames()
    val noDamageTo = this.damageRelations.noDamageTo.getNames()

    return DatabaseTypes(
        id,
        doubleDamageFrom,
        doubleDamageTo,
        halfDamageFrom,
        halfDamageTo,
        noDamageFrom,
        noDamageTo,
        name
    )
}

@JvmName("asDatabaseModelPokemonData")
suspend fun PokemonData.asDatabaseModel(): DatabasePokemon {
    val pokemonStats = this.getPokemonStats()
    val pokemonTypes = this.getPokemonTypes()

    val speciesData =
        PokeApi.retrofitService.getSpeciesByName(this.species.name)

    //Split the url to get the chain id
    val splitUrl = speciesData.evolutionChain.url.split("/")
    val chainID = splitUrl[splitUrl.size - 2]

    val evolutionChainData = PokeApi.retrofitService.getEvolutionChainById(chainID)

    val evolutionChain = evolutionChainData.getEvolutionChain()

    val flavor = speciesData.getFlavor()

    val genus = speciesData.getGenus()

    val sprite = this.sprites.frontDefault ?: ""

    return DatabasePokemon(
        id,
        height,
        name,
        speciesData.color.name,
        evolutionChain,
        flavor,
        genus,
        sprite,
        pokemonStats[0],
        pokemonStats[1],
        pokemonStats[2],
        pokemonStats[3],
        pokemonStats[4],
        pokemonStats[5],
        pokemonTypes,
        weight
    )
}