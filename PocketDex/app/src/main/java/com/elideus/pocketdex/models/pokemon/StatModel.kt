package com.elideus.pocketdex.models.pokemon

import android.util.Log
import com.elideus.pocketdex.network.PokeApi
import com.elideus.pocketdex.network.getNamesFromNamesAndUrl

class StatModel {
    var name = ""
    var value = 0

    constructor(
        name: String,
        value: Int
    ) {
        this.name = name
        this.value = value
    }

    companion object {
        fun getStat(statName: String, statValue: Int): StatModel {
            val statModel = StatModel(statName, statValue)

            return statModel
        }
    }
}