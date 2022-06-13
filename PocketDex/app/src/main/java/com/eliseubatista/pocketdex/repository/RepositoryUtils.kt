package com.eliseubatista.pocketdex.repository

import com.eliseubatista.pocketdex.database.DatabaseFavorites
import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.getNames
import com.eliseubatista.pocketdex.network.pokemons.*

@JvmName("asDatabaseTypeTypeData")
suspend fun TypeData.asDatabaseModel(): DatabaseTypes {

    val _doubleDamageFrom = this.damageRelations.doubleDamageFrom.getNames()
    val _doubleDamageTo = this.damageRelations.doubleDamageTo.getNames()
    val _halfDamageFrom = this.damageRelations.halfDamageFrom.getNames()
    val _halfDamageTo = this.damageRelations.halfDamageTo.getNames()
    val _noDamageFrom = this.damageRelations.noDamageFrom.getNames()
    val _noDamageTo = this.damageRelations.noDamageTo.getNames()

    val databaseType = DatabaseTypes(
        this.id,
        _doubleDamageFrom,
        _doubleDamageTo,
        _halfDamageFrom,
        _halfDamageTo,
        _noDamageFrom,
        _noDamageTo,
        this.name
    )

    return databaseType
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

    val pokemonMaleSprite = this.sprites.frontDefault ?: ""
    val pokemonFemaleSprite = this.sprites.frontFemale ?: ""

    val pokeDatabase = DatabasePokemon(
        this.id,
        this.height,
        this.name,
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
        this.weight
    )

    return pokeDatabase
}

@JvmName("asDomainModelDatabaseTypes")
fun DatabaseTypes.asDomainModel(): TypeModel {
    val type = TypeModel.fromDatabase(this)
    return type
}

@JvmName("asDomainModelDatabaseTypes")
fun List<DatabaseTypes>.asDomainModel(): List<TypeModel> {
    val types = mutableListOf<TypeModel>()

    for (element in this) {
        val type = TypeModel.fromDatabase(element)
        types.add(type)
    }

    return types
}

@JvmName("asDomainModelDatabasePokemon")
fun DatabasePokemon.asDomainModel(): PokemonModel {
    val pokemon = PokemonModel.fromDatabase(this)
    return pokemon
}

@JvmName("asDomainModelDatabasePokemon")
fun List<DatabasePokemon>.asDomainModel(): List<PokemonModel> {
    val pokemons = mutableListOf<PokemonModel>()

    for (element in this) {
        val pokemon = PokemonModel.fromDatabase(element)
        pokemons.add(pokemon)
    }

    return pokemons
}

