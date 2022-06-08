package com.eliseubatista.pocketdex.utils

import android.content.Context

fun pxToDp(context: Context, pxValue: Int): Int {
    val res = context.resources

    val density = res.displayMetrics.density
    val dpValue = (pxValue / density).toInt()

    return dpValue
}

fun dpToPx(context: Context, dpValue: Int): Int {
    val res = context.resources

    val density = res.displayMetrics.density
    val pxValue = (dpValue * density).toInt()

    return pxValue
}