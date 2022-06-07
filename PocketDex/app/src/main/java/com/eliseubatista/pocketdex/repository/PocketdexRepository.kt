package com.eliseubatista.pocketdex.repository

import androidx.lifecycle.LiveData
import com.eliseubatista.pocketdex.database.PocketdexDatabase
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.pokemons.TypeData
import com.eliseubatista.pocketdex.network.pokemons.asDatabaseTypeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PocketdexRepository(private val database: PocketdexDatabase) {

    //val pokemonTypes: LiveData<List<TypeModel>> = database.typesDao.getTypes()

    suspend fun refreshTypes() {
        withContext(Dispatchers.IO) {
            val typesGlobalData = PokeApi.retrofitService.getTypes(100, 0)

            val typesDataList = mutableListOf<TypeData>()

            for (typeResult in typesGlobalData.results) {
                val typeData = PokeApi.retrofitService.getTypeByName(typeResult.name)
                typesDataList.add(typeData)
            }

            database.typesDao.insertAll(*typesDataList.asDatabaseTypeData())
        }
    }

}