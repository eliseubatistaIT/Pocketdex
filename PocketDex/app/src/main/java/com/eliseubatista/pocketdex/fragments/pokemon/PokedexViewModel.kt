package com.eliseubatista.pocketdex.fragments.pokemon

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eliseubatista.pocketdex.database.getDatabase
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.repository.PocketdexRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokedexViewModel(val application: Application) : ViewModel() {

    private val database = getDatabase(application)
    private val pocketdexRepository = PocketdexRepository(database)

    val types = pocketdexRepository.pokemonTypes
    val pokemons = pocketdexRepository.pokemons

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

        _isLoadingMorePokemons.value = true

        coroutineScope.launch {

            pocketdexRepository.refreshTypes(application)
            pocketdexRepository.refreshPokemons(application,10, pokemonsLoaded)

            pokemonsLoaded += 10

            _isLoadingMorePokemons.value = false
        }
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
                return PokedexViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}