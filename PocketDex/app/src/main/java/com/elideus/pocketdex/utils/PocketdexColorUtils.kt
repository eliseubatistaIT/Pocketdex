package com.elideus.pocketdex.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.red
import com.elideus.pocketdex.R
import com.elideus.pocketdex.models.TypeModel

//Function to get a pokemon color by its type
fun getPokemonBackgroundColor(context: Context, pokemonType: String): Int {
    val res = context.resources

    var newColor = res.getColor(R.color.pokemon_unknown)

    when (pokemonType) {
        "normal" -> newColor = getColor(context, R.color.pokemon_normal)
        "fighting" -> newColor = getColor(context, R.color.pokemon_fighting)
        "flying" -> newColor = getColor(context, R.color.pokemon_flying)
        "poison" -> newColor = getColor(context, R.color.pokemon_poison)
        "ground" -> newColor = getColor(context, R.color.pokemon_ground)
        "rock" -> newColor = getColor(context, R.color.pokemon_rock)
        "bug" -> newColor = getColor(context, R.color.pokemon_bug)
        "ghost" -> newColor = getColor(context, R.color.pokemon_ghost)
        "steel" -> newColor = getColor(context, R.color.pokemon_steel)
        "fire" -> newColor = getColor(context, R.color.pokemon_fire)
        "water" -> newColor = getColor(context, R.color.pokemon_water)
        "grass" -> newColor = getColor(context, R.color.pokemon_grass)
        "electric" -> newColor = getColor(context, R.color.pokemon_electric)
        "psychic" -> newColor = getColor(context, R.color.pokemon_psychic)
        "ice" -> newColor = getColor(context, R.color.pokemon_ice)
        "dragon" -> newColor = getColor(context, R.color.pokemon_dragon)
        "dark" -> newColor = getColor(context, R.color.pokemon_dark)
        "fairy" -> newColor = getColor(context, R.color.pokemon_fairy)
        "shadow" -> newColor = getColor(context, R.color.pokemon_shadow)
        else -> newColor = getColor(context, R.color.pokemon_unknown)
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