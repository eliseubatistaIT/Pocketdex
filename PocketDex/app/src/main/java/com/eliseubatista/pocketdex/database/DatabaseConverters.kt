package com.eliseubatista.pocketdex.database

import androidx.room.TypeConverter
import com.eliseubatista.pocketdex.models.pokemons.StatModel

class DatabaseTypeConverters {

    @TypeConverter
    fun fromStats(stats: DatabasePokemonStatsModels): String {
        var statInString = ""

        for (stat in stats.pokemonStats) {
            statInString += "${stat.name}/${stat.value}"
        }

        return statInString
    }

    @TypeConverter
    fun toStats(statInString: String): DatabasePokemonStatsModels {

        val statsList = mutableListOf<StatModel>()

        val splittedList = statInString.split("/")

        for (element in splittedList) {
            val splittedStat = element.split(";")
            val stat = StatModel(splittedStat[0], splittedStat[1].toInt())
            statsList.add(stat)
        }

        val databaseStatModel = DatabasePokemonStatsModels()
        databaseStatModel.pokemonStats = statsList

        return databaseStatModel
    }

    @TypeConverter
    fun fromStringList(listOfStrings: List<String>): String {
        var finalString = ""

        for (s in listOfStrings) {
            finalString = finalString + ";" + s
        }

        return finalString
    }

    @TypeConverter
    fun toStringList(storedString: String): List<String> {
        val stringList = storedString.split(";")

        return stringList
    }

    @TypeConverter
    fun fromIntList(listOfInts: List<Int>): String {
        var finalString = ""

        for (i in listOfInts) {
            finalString = finalString + ";" + i
        }

        return finalString
    }

    @TypeConverter
    fun toIntList(storedString: String): List<Int> {
        val stringList = storedString.split(";")

        val intList = mutableListOf<Int>()

        for (s in stringList) {
            intList.add(s.toInt())
        }

        return intList
    }
}