package com.elideus.pocketdex.fragments.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elideus.pocketdex.network.BaseNameAndUrl
import com.elideus.pocketdex.network.PokeApi
import com.elideus.pocketdex.network.PokemonDetailedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokedexViewModel : ViewModel() {

    private var _listOfPokemons = MutableLiveData<List<PokemonDetailedData>>()
    val listOfPokemons: LiveData<List<PokemonDetailedData>>
        get() = _listOfPokemons

    private var coroutineJob = Job()
    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    init {
        getPokemons()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }

    private fun getPokemons() {

        coroutineScope.launch {
            try {
                //Try to get all the pokemons in a specific range
                val listOfPokemonsData = PokeApi.retrofitService.getPokemons(30, 0).results

                //Create a list to store the pokemon data
                val listOfPokemonsDetails = mutableListOf<PokemonDetailedData>()

                //For each pokemon retrieved, get its data
                for (pokeData in listOfPokemonsData) {
                    getPokemonByName(pokeData.name)?.let {
                        listOfPokemonsDetails.add(it)
                    }
                }

                //Update the live data to refresh the observers
                _listOfPokemons.value = listOfPokemonsDetails
            } catch (e: Exception) {
                //If we get an exception, create an empty list
                Log.i("ERRO", e.toString())
                _listOfPokemons.value = ArrayList()
            }
        }
    }

    //Function to get a pokemon by name
    private suspend fun getPokemonByName(name: String): PokemonDetailedData? {
        Log.i("FETCHING", name)

        try {
            //Try to get a pokemon data, and we succeed, return the data
            val pokemonDetailedData = PokeApi.retrofitService.getPokemonByName(name)
            return pokemonDetailedData
        } catch (e: Exception) {
            //Otherwise, return null
            Log.i("ERRO FETCHING", e.toString())
            return null
        }

    }
}