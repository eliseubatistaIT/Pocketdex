package com.eliseubatista.pocketdex.fragments.pokemon

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eliseubatista.pocketdex.database.DatabaseFavorites
import com.eliseubatista.pocketdex.database.getDatabase
import com.eliseubatista.pocketdex.models.pokemons.EvolutionChainModel
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.repository.PocketdexRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(val application: Application, val pokemonName: String) : ViewModel() {

    private val database = getDatabase(application)
    private val pocketdexRepository = PocketdexRepository(database)

    lateinit var pokeFirstType: TypeModel
    var pokeEvolutionChain = mutableListOf<EvolutionChainModel>()

    private var _pokemon = MutableLiveData<PokemonModel>()
    val pokemon: LiveData<PokemonModel>
        get() = _pokemon

    private var _isInFavorites = MutableLiveData<Boolean>()
    val isInFavorites: LiveData<Boolean>
        get() = _isInFavorites

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

            val pokemonModel = pocketdexRepository.getPokemonByName(application, pokemonName)

            pokeFirstType =
                pocketdexRepository.getTypeByName(application, pokemonModel!!.types[0])!!

            //for each evolution chain
            for (evolutionChain in pokemonModel.evolutionChain) {

                if (!evolutionChain.contains(pokemonName)) {
                    continue
                }

                val evolutionChainModel = EvolutionChainModel(evolutionChain)

                //Split the url to get the chain id
                val splitChain = evolutionChain.split(":")

                for (evolution in splitChain) {
                    val evolutionPokemon =
                        pocketdexRepository.getPokemonByName(application, evolution)

                    evolutionChainModel.evolutions.add(evolutionPokemon!!)
                }


                pokeEvolutionChain.add(evolutionChainModel)
            }

            val favorite = pocketdexRepository.getFavoriteByName(pokemonName)

            _isInFavorites.value = favorite != null

            _pokemon.value = pokemonModel

            _isLoadingPokemon.value = false
        }
    }

    fun addOrRemoveFavorite() {

        coroutineScope.launch {
            val favoriteInDatabase = pocketdexRepository.getFavoriteByName(pokemonName)

            if (favoriteInDatabase == null) {
                Log.i("FAV", "Adding ${pokemonName} to favorites")

                coroutineScope.launch {
                    val favoriteData = DatabaseFavorites(
                        0,
                        pokemonName,
                        "pokemon"
                    )

                    pocketdexRepository.addToFavorites(favoriteData)
                    _isInFavorites.value = true
                }
            } else {
                Log.i("FAV", "Removing ${pokemonName} from favorites")

                pocketdexRepository.removeFromFavorites(favoriteInDatabase)
                _isInFavorites.value = false
            }
        }


    }

    class Factory(val application: Application, val pokemonName: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)) {
                return PokemonDetailsViewModel(application, pokemonName) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}