package com.eliseubatista.pocketdex.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.eliseubatista.pocketdex.R

fun getImageScaleByEvolutionChain(
    pokemonName: String,
    evolutionChains: List<String>
): Float {
    var pokemonIndex = 0

    //for each evolution chain
    for (evolutionChain in evolutionChains) {

        //Split the url to get the chain id
        val splitChain = evolutionChain.split(":")


        for ((index, evolution) in splitChain.withIndex()) {
            if (pokemonName == evolution) {

                pokemonIndex = index
                break
            }
        }
    }

    var imageScale = 1.0f


    when (pokemonIndex) {
        0 -> imageScale = 1.4f
        1 -> imageScale = 1.15f
        else -> imageScale = 1.0f
    }

    return imageScale
}

//Function to get a pokemon type logo
fun getPokemonTypeLogoImage(context: Context, pokemonType: String): Drawable? {
    var newLogo = ContextCompat.getDrawable(context, R.drawable.img_normal_type_logo)

    when (pokemonType) {
        "normal" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_normal_type_logo)
        "fighting" -> newLogo =
            ContextCompat.getDrawable(context, R.drawable.img_fighting_type_logo)
        "flying" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_flying_type_logo)
        "poison" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_poison_type_logo)
        "ground" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_ground_type_logo)
        "rock" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_rock_type_logo)
        "bug" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_bug_type_logo)
        "ghost" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_ghost_type_logo)
        "steel" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_steel_type_logo)
        "fire" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_fire_type_logo)
        "water" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_water_type_logo)
        "grass" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_grass_type_logo)
        "electric" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_eletric_type_logo)
        "psychic" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_psychic_type_logo)
        "ice" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_ice_type_logo)
        "dragon" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_dragon_type_logo)
        "dark" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_dark_type_logo)
        "fairy" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_fairy_type_logo)
        "shadow" -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_dark_type_logo)
        else -> newLogo = ContextCompat.getDrawable(context, R.drawable.img_normal_type_logo)
    }

    return newLogo
}

//Function to get a pokemon type text image
fun getPokemonTypeTextImage(context: Context, pokemonType: String): Drawable? {
    val res = context.resources

    var newTextImage = ContextCompat.getDrawable(context, R.drawable.img_normal_type_text)

    when (pokemonType) {
        "normal" -> newTextImage =
            ContextCompat.getDrawable(context, R.drawable.img_normal_type_text)
        "fighting" -> newTextImage =
            ContextCompat.getDrawable(context, R.drawable.img_fighting_type_text)
        "flying" -> newTextImage =
            ContextCompat.getDrawable(context, R.drawable.img_flying_type_text)
        "poison" -> newTextImage =
            ContextCompat.getDrawable(context, R.drawable.img_poison_type_text)
        "ground" -> newTextImage =
            ContextCompat.getDrawable(context, R.drawable.img_ground_type_text)
        "rock" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_rock_type_text)
        "bug" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_bug_type_text)
        "ghost" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_ghost_type_text)
        "steel" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_steel_type_text)
        "fire" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_fire_type_text)
        "water" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_water_type_text)
        "grass" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_grass_type_text)
        "electric" -> newTextImage =
            ContextCompat.getDrawable(context, R.drawable.img_eletric_type_text)
        "psychic" -> newTextImage =
            ContextCompat.getDrawable(context, R.drawable.img_psychic_type_text)
        "ice" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_ice_type_text)
        "dragon" -> newTextImage =
            ContextCompat.getDrawable(context, R.drawable.img_dragon_type_text)
        "dark" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_dark_type_text)
        "fairy" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_fairy_type_text)
        "shadow" -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_dark_type_text)
        else -> newTextImage = ContextCompat.getDrawable(context, R.drawable.img_normal_type_text)
    }

    return newTextImage
}
