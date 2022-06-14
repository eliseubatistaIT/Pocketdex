package com.eliseubatista.pocketdex.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.eliseubatista.pocketdex.R

fun FragmentTransaction.replaceFragment(fragment: Fragment, layout: Int = R.id.fragment_container) {
    this.replace(layout, fragment)
    this.addToBackStack("BACKSTACK")
    this.commit()
}

fun FragmentTransaction.addFragment(fragment: Fragment, layout: Int = R.id.fragment_container) {
    this.add(layout, fragment)
    this.addToBackStack("BACKSTACK")
    this.commit()
}