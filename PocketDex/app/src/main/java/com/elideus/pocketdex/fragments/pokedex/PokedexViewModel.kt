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

    private var _isLoadingMorePokemons = MutableLiveData<Boolean>()
    val isLoadingMorePokemons: LiveData<Boolean>
        get() = _isLoadingMorePokemons

    private var coroutineJob = Job()
    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    private var pokemonsLoaded = 0;

    init {
        getMorePokemons()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }

    fun getMorePokemons() {

        //If we are already loading and waiting for more pokemons, do nothing
        if (_isLoadingMorePokemons.value == true) {
            return
        }

        Log.i("END", "FETCHING MORE POKEMONS")

        _isLoadingMorePokemons.value = true

        coroutineScope.launch {
            //Store how many pokemons we already loaded, to save some processing
            val previousLoadedPokemons = pokemonsLoaded
            //Also, we want to load ten more
            pokemonsLoaded += 10

            try {
                //Try to get all the pokemons in a specific range
                val pokemonsData =
                    PokeApi.retrofitService.getPokemons(pokemonsLoaded, previousLoadedPokemons)

                //Copy the value of the listOfPokemons (if its null, create a new list)
                val listOfPokemonsDetails =
                    _listOfPokemons.value?.toMutableList() ?: mutableListOf<PokemonDetailedData>()

                //For each pokemon retrieved, get its data
                for (pokeData in pokemonsData.results) {
                    getPokemonByName(pokeData.name)?.let {
                        listOfPokemonsDetails.add(it)
                    }
                }

                //Update the live data to refresh the observers
                _listOfPokemons.value = listOfPokemonsDetails

                _isLoadingMorePokemons.value = false

            } catch (e: Exception) {
                //If we get an exception, create an empty list
                Log.i("ERRO", e.toString())
                _listOfPokemons.value = ArrayList()

                _isLoadingMorePokemons.value = false
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