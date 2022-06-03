package com.elideus.pocketdex.utils

import android.content.Context
import android.graphics.Color
import com.elideus.pocketdex.R
import com.elideus.pocketdex.models.TypeModel

fun getPokemonBackgroundColor(context: Context, pokemonType: String): Int {
    val res = context.resources

    var newColor = res.getColor(R.color.pokemon_unknown)

    when (pokemonType) {
        "normal" -> newColor = res.getColor(R.color.pokemon_normal)
        "fighting" -> newColor = res.getColor(R.color.pokemon_fighting)
        "flying" -> newColor = res.getColor(R.color.pokemon_flying)
        "poison" -> newColor = res.getColor(R.color.pokemon_poison)
        "ground" -> newColor = res.getColor(R.color.pokemon_ground)
        "rock" -> newColor = res.getColor(R.color.pokemon_rock)
        "bug" -> newColor = res.getColor(R.color.pokemon_bug)
        "ghost" -> newColor = res.getColor(R.color.pokemon_ghost)
        "steel" -> newColor = res.getColor(R.color.pokemon_steel)
        "fire" -> newColor = res.getColor(R.color.pokemon_fire)
        "water" -> newColor = res.getColor(R.color.pokemon_water)
        "grass" -> newColor = res.getColor(R.color.pokemon_grass)
        "electric" -> newColor = res.getColor(R.color.pokemon_electric)
        "psychic" -> newColor = res.getColor(R.color.pokemon_psychic)
        "ice" -> newColor = res.getColor(R.color.pokemon_ice)
        "dragon" -> newColor = res.getColor(R.color.pokemon_dragon)
        "dark" -> newColor = res.getColor(R.color.pokemon_dark)
        "fairy" -> newColor = res.getColor(R.color.pokemon_fairy)
        "shadow" -> newColor = res.getColor(R.color.pokemon_shadow)
        else -> newColor = res.getColor(R.color.pokemon_unknown)
    }

    return newColor
}

fun manipulateColor(color: Int, factor: Float): Int {
    val a = Color.alpha(color)
    val r = Math.round(Color.red(color) * factor)
    val g = Math.round(Color.green(color) * factor)
    val b = Math.round(Color.blue(color) * factor)

    //return the new color, but make sure the values are not above 255
    return Color.argb(a, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255))
}

fun getPokemonTextColor(context: Context, pokemonType: String): Int {
    val res = context.resources

    var newColor = res.getColor(R.color.pokemon_unknown)

    when (pokemonType) {
        "normal" -> newColor = res.getColor(R.color.black)
        "fighting" -> newColor = res.getColor(R.color.black)
        "flying" -> newColor = res.getColor(R.color.black)
        "poison" -> newColor = res.getColor(R.color.white)
        "ground" -> newColor = res.getColor(R.color.white)
        "rock" -> newColor = res.getColor(R.color.white)
        "bug" -> newColor = res.getColor(R.color.black)
        "ghost" -> newColor = res.getColor(R.color.white)
        "steel" -> newColor = res.getColor(R.color.white)
        "fire" -> newColor = res.getColor(R.color.white)
        "water" -> newColor = res.getColor(R.color.white)
        "grass" -> newColor = res.getColor(R.color.black)
        "electric" -> newColor = res.getColor(R.color.black)
        "psychic" -> newColor = res.getColor(R.color.white)
        "ice" -> newColor = res.getColor(R.color.black)
        "dragon" -> newColor = res.getColor(R.color.white)
        "dark" -> newColor = res.getColor(R.color.white)
        "fairy" -> newColor = res.getColor(R.color.black)
        "shadow" -> newColor = res.getColor(R.color.white)
        else -> newColor = res.getColor(R.color.white)
    }

    return newColor
}