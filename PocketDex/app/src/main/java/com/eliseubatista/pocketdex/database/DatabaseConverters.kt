package com.eliseubatista.pocketdex.database

import androidx.room.TypeConverter

class DatabaseTypeConverters {

    @TypeConverter
    fun fromStringList(listOfStrings: List<String>): String {
        var finalString = ""

        for ((index, s) in listOfStrings.withIndex()) {

            if (index == 0) {
                finalString = s
            } else {
                finalString += ";$s"
            }
        }

        return finalString
    }

    @TypeConverter
    fun toStringList(storedString: String): List<String> {

        return storedString.split(";")
    }

    @TypeConverter
    fun fromIntList(listOfInts: List<Int>): String {
        var finalString = ""

        for ((index, i) in listOfInts.withIndex()) {

            if (index == 0) {
                finalString = i.toString()
            } else {
                finalString += ";$i"
            }
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