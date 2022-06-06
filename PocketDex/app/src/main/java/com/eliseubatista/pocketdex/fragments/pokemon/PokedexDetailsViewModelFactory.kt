package com.eliseubatista.pocketdex.fragments.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonDetailsViewModelFactory(private val pokemonName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)) {
            return PokemonDetailsViewModel(pokemonName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}