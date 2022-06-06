package com.eliseubatista.pocketdex.utils

import java.util.*

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

//Function used to format pokemon, item and locations names
fun formatPocketdexObjectDescription(description: String): String {
    if (description.isEmpty()) {
        return description
    }

    val newDescription = description
    val tokenizer = StringTokenizer(newDescription)
    val builder = StringBuilder()

    while (tokenizer.hasMoreTokens()) {
        val string = tokenizer.nextToken();
        builder.append(string).append(" ");
    }

    return builder.toString()
}
