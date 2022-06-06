package com.elideus.pocketdex.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat.getColor
import com.elideus.pocketdex.R

//Function to get a pokemon color
fun getPokemonBackgroundColor(context: Context, pokemonColorName: String): Int {
    val res = context.resources

    var newColor = res.getColor(R.color.pokemon_white)

    when (pokemonColorName) {
        "black" -> newColor = getColor(context, R.color.pokemon_black)
        "blue" -> newColor = getColor(context, R.color.pokemon_blue)
        "brown" -> newColor = getColor(context, R.color.pokemon_brown)
        "gray" -> newColor = getColor(context, R.color.pokemon_gray)
        "green" -> newColor = getColor(context, R.color.pokemon_green)
        "pink" -> newColor = getColor(context, R.color.pokemon_pink)
        "purple" -> newColor = getColor(context, R.color.pokemon_purple)
        "red" -> newColor = getColor(context, R.color.pokemon_red)
        "white" -> newColor = getColor(context, R.color.pokemon_white)
        "yellow" -> newColor = getColor(context, R.color.pokemon_yellow)
        else -> newColor = getColor(context, R.color.pokemon_white)
    }

    return newColor
}

//Function to get a pokemon type color
fun getPokemonTypeColor(context: Context, pokemonType: String): Int {
    val res = context.resources

    var newColor = res.getColor(R.color.type_unknown)

    when (pokemonType) {
        "normal" -> newColor = getColor(context, R.color.type_normal)
        "fighting" -> newColor = getColor(context, R.color.type_fighting)
        "flying" -> newColor = getColor(context, R.color.type_flying)
        "poison" -> newColor = getColor(context, R.color.type_poison)
        "ground" -> newColor = getColor(context, R.color.type_ground)
        "rock" -> newColor = getColor(context, R.color.type_rock)
        "bug" -> newColor = getColor(context, R.color.type_bug)
        "ghost" -> newColor = getColor(context, R.color.type_ghost)
        "steel" -> newColor = getColor(context, R.color.type_steel)
        "fire" -> newColor = getColor(context, R.color.type_fire)
        "water" -> newColor = getColor(context, R.color.type_water)
        "grass" -> newColor = getColor(context, R.color.type_grass)
        "electric" -> newColor = getColor(context, R.color.type_electric)
        "psychic" -> newColor = getColor(context, R.color.type_psychic)
        "ice" -> newColor = getColor(context, R.color.type_ice)
        "dragon" -> newColor = getColor(context, R.color.type_dragon)
        "black" -> newColor = getColor(context, R.color.type_dark)
        "fairy" -> newColor = getColor(context, R.color.type_fairy)
        "shadow" -> newColor = getColor(context, R.color.type_shadow)
        else -> newColor = getColor(context, R.color.type_unknown)
    }

    return newColor
}

//Function to calculate if the text on top of backgroundColor should be black or white
fun getTextColorByBackgroundColor(context: Context, backgroundColor: Int): Int {
    val res = context.resources

    //By default, the text color is white
    var newColor = getColor(context, R.color.white)

    val colorThreshold =
        (Color.red(backgroundColor) * 0.299) + (Color.green(backgroundColor) * 0.587) + (Color.blue(
            backgroundColor
        ) * 0.114)

    //If the color brightness treshold is greater than a specific value, the text should be black
    if (colorThreshold > 186) {
        newColor = getColor(context, R.color.black)
    }

    return newColor
}

/*
Function to manipulate the color brightness by a specific factor.
If the factor is less than 1, the color would be darker
If the factor is greater than 1, the color would be brighter
If the factor is 1, the color would be the same
 */
fun manipulateColor(color: Int, factor: Float): Int {
    val a = Color.alpha(color)
    val r = Math.round(Color.red(color) * factor)
    val g = Math.round(Color.green(color) * factor)
    val b = Math.round(Color.blue(color) * factor)

    //return the new color, but make sure the values are not above 255
    return Color.argb(a, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255))
}