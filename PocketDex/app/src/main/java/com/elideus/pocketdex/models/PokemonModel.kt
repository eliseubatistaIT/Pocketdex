package com.elideus.pocketdex.models

import android.util.Log
import com.elideus.pocketdex.network.*

class PokemonModel {

    var id = 0
    var abilities = listOf<String>()
    var baseExperience = 0
    var height = 0
    var heldItems = listOf<ItemModel>()
    var moves = listOf<String>()
    var name = ""
    var specie = SpecieModel()
    var maleSprite = ""
    var femaleSprite = ""
    var types = listOf<TypeModel>()
    var weight = 0

    constructor(id: Int, name: String, maleSprite: String, types: List<TypeModel>) {
        this.id = id
        this.name = name
        this.maleSprite = maleSprite
        this.types = types;
    }

    constructor(
        id: Int,
        abilities: List<String>,
        baseExperience: Int,
        height: Int,
        heldItems: List<ItemModel>,
        moves: List<String>,
        name: String,
        specie: SpecieModel,
        maleSprite: String,
        femaleSprite: String,
        types: List<TypeModel>,
        weight: Int
    ) {
        this.id = id
        this.abilities = abilities
        this.baseExperience = baseExperience
        this.height = height
        this.heldItems = heldItems
        this.moves = moves
        this.name = name
        this.specie = specie
        this.maleSprite = maleSprite
        this.femaleSprite = femaleSprite
        this.types = types
        this.weight = weight
    }

    companion object {
        suspend fun getPokemonShortData(pokemonName: String): PokemonModel? {
            var pokemon: PokemonModel? = null

            try {
                //Try to get a pokemon data, and we succeed, return the data
                val pokemonDetailedData = PokeApi.retrofitService.getPokemonByName(pokemonName)
                Log.i("FETCHING TYPES", pokemonDetailedData.name)
                val pokemonTypes = getPokemonTypes(pokemonDetailedData.types)
                val pokemonMaleSprite = pokemonDetailedData.sprites.frontDefault ?: ""

                pokemon = PokemonModel(
                    pokemonDetailedData.id,
                    pokemonDetailedData.name,
                    pokemonMaleSprite,
                    pokemonTypes
                )
            } catch (e: Exception) {
                //Otherwise, return null
                Log.i("ERROR FETCHING POKEMON", e.toString())
                pokemon = null
            }

            return pokemon
        }

        suspend fun getPokemonTypes(pokemonTypesData: List<PokemonTypeData>): List<TypeModel> {

            val pokemonTypesList = mutableListOf<TypeModel>()

            try {
                for (typeData in pokemonTypesData) {

                    val typeDetailedData = PokeApi.retrofitService.getTypeByName(typeData.type.name)

                    var doubleDamageFrom =
                        getNamesFromNamesAndUrl(typeDetailedData.damageRelations.doubleDamageFrom)
                    var doubleDamageTo =
                        getNamesFromNamesAndUrl(typeDetailedData.damageRelations.doubleDamageTo)
                    var halfDamageFrom =
                        getNamesFromNamesAndUrl(typeDetailedData.damageRelations.halfDamageFrom)
                    var halfDamageTo =
                        getNamesFromNamesAndUrl(typeDetailedData.damageRelations.halfDamageTo)
                    var noDamageFrom =
                        getNamesFromNamesAndUrl(typeDetailedData.damageRelations.noDamageFrom)
                    var noDamageTo =
                        getNamesFromNamesAndUrl(typeDetailedData.damageRelations.noDamageTo)

                    val pokemonType = TypeModel(
                        typeDetailedData.id,
                        doubleDamageFrom,
                        doubleDamageTo,
                        halfDamageFrom,
                        halfDamageTo,
                        noDamageFrom,
                        noDamageTo,
                        typeDetailedData.name
                    )

                    pokemonTypesList.add(pokemonType)
                }
            } catch (e: Exception) {
                //Otherwise, return null
                Log.i("ERROR FETCHING TYPES", e.toString())
            }

            return pokemonTypesList
        }
    }
}