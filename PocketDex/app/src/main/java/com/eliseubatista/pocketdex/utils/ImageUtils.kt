package com.eliseubatista.pocketdex.utils

import android.graphics.Bitmap
import android.view.ViewGroup

fun getImageScaleByEvolutionChain(
    pokemonName: String,
    evolutionChain: List<String>
): Float {
    var pokemonIndex = 0

    for ((index, evolutionName) in evolutionChain.withIndex()) {
        if (pokemonName == evolutionName) {
            pokemonIndex = index
            break
        }
    }

    var imageScale = 1.0f

    when (pokemonIndex) {
        0 -> imageScale = 1.8f
        1 -> imageScale = 1.3f
        else -> imageScale = 1.0f
    }

    return imageScale
}