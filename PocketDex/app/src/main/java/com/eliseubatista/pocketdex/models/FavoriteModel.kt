package com.eliseubatista.pocketdex.models

import com.eliseubatista.pocketdex.database.DatabaseFavorites
import com.eliseubatista.pocketdex.database.DatabaseTypes
import com.eliseubatista.pocketdex.models.pokemons.TypeModel

class FavoriteModel {
    var id = 0
    var name = ""
    var category = ""

    constructor(
        id: Int,
        name: String,
        category: String
    ) {
        this.id = id
        this.name = name
        this.category = category
    }

    override fun toString(): String {
        return "\nFavorite: ${id}, ${name}, ${category}\n"
    }

    companion object {

        fun fromDatabase(databaseFavorites: DatabaseFavorites): FavoriteModel {

            return FavoriteModel(
                databaseFavorites.id,
                databaseFavorites.name,
                databaseFavorites.category,
            )
        }
    }
}