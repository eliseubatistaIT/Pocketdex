package com.elideus.pocketdex.fragments.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elideus.pocketdex.models.pokemon.PokemonModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(private val pokemonName: String) : ViewModel() {

    private var _pokemon = MutableLiveData<PokemonModel>()
    val pokemon: LiveData<PokemonModel>
        get() = _pokemon

    private var _isLoadingPokemon = MutableLiveData<Boolean>()
    val isLoadingPokemon: LiveData<Boolean>
        get() = _isLoadingPokemon

    private var coroutineJob = Job()
    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    init {
        getPokemonData()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }

    fun getPokemonData() {

        //If we are already loading and waiting for the pokemon data, do nothing
        if (_isLoadingPokemon.value == true) {
            return
        }

        _isLoadingPokemon.value = true

        coroutineScope.launch {
            try {
                _pokemon.value = PokemonModel.getPokemonData(pokemonName)

                _isLoadingPokemon.value = false

            } catch (e: Exception) {
                //If we get an exception, create an empty list
                Log.i("ERRO", e.toString())

                _isLoadingPokemon.value = false
            }
        }
    }
}