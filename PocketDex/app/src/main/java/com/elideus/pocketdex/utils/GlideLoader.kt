package com.elideus.pocketdex.utils

import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide

fun loadImageWithGlide(imageUrl: String, imageView: ImageView) {
    val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()

    Glide.with(imageView.context)
        .load(imgUri)
        .into(imageView)
}