package com.eliseubatista.pocketdex.repository

import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.getNames
import com.eliseubatista.pocketdex.network.pokemons.*

suspend fun TypeApiToTypeDatabase(typeApiData: TypeData): DatabaseTypes {

    val _doubleDamageFrom = typeApiData.damageRelations.doubleDamageFrom.getNames()
    val _doubleDamageTo = typeApiData.damageRelations.doubleDamageTo.getNames()
    val _halfDamageFrom = typeApiData.damageRelations.halfDamageFrom.getNames()
    val _halfDamageTo = typeApiData.damageRelations.halfDamageTo.getNames()
    val _noDamageFrom = typeApiData.damageRelations.noDamageFrom.getNames()
    val _noDamageTo = typeApiData.damageRelations.noDamageTo.getNames()

    val databaseType = DatabaseTypes(
        typeApiData.id,
        _doubleDamageFrom,
        _doubleDamageTo,
        _halfDamageFrom,
        _halfDamageTo,
        _noDamageFrom,
        _noDamageTo,
        typeApiData.name
    )

    return databaseType
}

suspend fun PokemonApiToPokemonDatabase(pokemonApiData: PokemonData): DatabasePokemon {
    val pokemonStats = pokemonApiData.getPokemonStats()
    val pokemonTypes = pokemonApiData.getPokemonTypes()
    val speciesData =
        PokeApi.retrofitService.getSpeciesByName(pokemonApiData.species.name)

    //Split the url to get the chain id
    val splitUrl = speciesData.evolutionChain.url.split("/")
    val chainID = splitUrl[splitUrl.size - 2]

    val evolutionChainData = PokeApi.retrofitService.getEvolutionChainById(chainID)

    val evolutionChain = evolutionChainData.getEvolutionChain()

    val flavor = speciesData.getFlavor()

    val genus = speciesData.getGenus()

    val pokemonMaleSprite = pokemonApiData.sprites.frontDefault ?: ""
    val pokemonFemaleSprite = pokemonApiData.sprites.frontFemale ?: ""

    val pokeDatabase = DatabasePokemon(
        pokemonApiData.id,
        pokemonApiData.height,
        pokemonApiData.name,
        speciesData.color.name,
        evolutionChain,
        flavor,
        genus,
        pokemonMaleSprite,
        pokemonFemaleSprite,
        pokemonStats[0],
        pokemonStats[1],
        pokemonStats[2],
        pokemonStats[3],
        pokemonStats[4],
        pokemonStats[5],
        pokemonTypes,
        pokemonApiData.weight
    )

    return pokeDatabase
}