package com.eliseubatista.pocketdex.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//Function to load and image (with the image Url) into a image view
fun loadImageWithGlide(imageUrl: String, imageView: ImageView) {
    val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()

    Glide.with(imageView.context)
        .load(imgUri)
        .into(imageView)
}
