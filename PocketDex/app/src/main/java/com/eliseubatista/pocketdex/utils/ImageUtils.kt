package com.eliseubatista.pocketdex.utils

import android.graphics.Bitmap
import android.view.ViewGroup

//Function to get a pokemon color
fun getImageViewSizeByEvolutionChain(
    pokemonName: String,
    evolutionChain: List<String>
): Int {
    var pokemonIndex = 0

    for ((index, evolutionName) in evolutionChain.withIndex()) {
        if (pokemonName == evolutionName) {
            pokemonIndex = index
            break
        }
    }

    /*
    var imageViewSize = 325

    when (pokemonIndex) {
        0 -> imageViewSize = 300
        1 -> imageViewSize = 210
        else -> imageViewSize = 150
    }
    */

    var imageViewSize = 288

    when (pokemonIndex) {
        0 -> imageViewSize = 96
        1 -> imageViewSize = 96
        else -> imageViewSize = 96
    }

    return imageViewSize
}

fun scaleBitmapImage(originalImage: Bitmap, scaleFactor: Float): Bitmap? {

    val width = (originalImage.width * scaleFactor).toInt()
    val height = (originalImage.height * scaleFactor).toInt()

    return Bitmap.createScaledBitmap(originalImage, width, height, true)
}