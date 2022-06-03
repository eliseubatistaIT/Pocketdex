package com.elideus.pocketdex.utils

import android.util.Log

//Function used to format pokemon, item and locations names
fun formatPocketdexObjectName(name: String): String {
    if (name.isEmpty()) {
        return name
    }

    //Make sure first char is uppercase
    val upperCaseName = name.toCharArray()
    upperCaseName[0] = upperCaseName[0].uppercaseChar()

    var newName = String(upperCaseName)

    //For names like "nidoran-f" and "nidoran-m"
    if (newName.contains("-f")) {
        newName = newName.replace("-f", " Female")
    } else if (newName.contains("-m")) {
        newName = newName.replace("-m", " Male")
    }

    return newName
}