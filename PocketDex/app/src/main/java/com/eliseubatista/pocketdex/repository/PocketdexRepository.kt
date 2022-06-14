package com.eliseubatista.pocketdex.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.eliseubatista.pocketdex.database.PocketdexDatabase
import com.eliseubatista.pocketdex.database.favorites.DatabaseFavorites
import com.eliseubatista.pocketdex.database.items.DatabaseItemCategories
import com.eliseubatista.pocketdex.database.items.DatabaseItems
import com.eliseubatista.pocketdex.database.pokemons.DatabasePokemon
import com.eliseubatista.pocketdex.database.pokemons.DatabaseTypes
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.items.asDatabaseModel
import com.eliseubatista.pocketdex.network.pokemons.asDatabaseModel
import com.eliseubatista.pocketdex.utils.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PocketdexRepository(database: PocketdexDatabase) {

    val favoritesRepository = FavoriteRepository(database)
    val pokedexRepository = PokedexRepository(database)
    val itemsRepository = ItemsRepository(database)

    //FAVORITES  -------------------------------------------------------------------------------------------

    class FavoriteRepository(private val database: PocketdexDatabase) {
        val favoritePokemons: LiveData<List<DatabaseFavorites>> =
            database.favoritesDao.getFavoritePokemons()

        val favoriteItems: LiveData<List<DatabaseFavorites>> =
            database.favoritesDao.getFavoriteItems()

        val favoriteLocations: LiveData<List<DatabaseFavorites>> =
            database.favoritesDao.getFavoriteLocations()

        suspend fun addToFavorites(favorite: DatabaseFavorites) {
            withContext(Dispatchers.IO) {
                database.favoritesDao.insert(favorite)
            }
        }

        suspend fun removeFromFavorites(favorite: DatabaseFavorites?) {
            if (favorite == null) {
                return
            }

            withContext(Dispatchers.IO) {
                database.favoritesDao.delete(favorite)
            }
        }

        suspend fun getFavoriteByName(name: String): DatabaseFavorites? {

            var favorite: DatabaseFavorites?

            withContext(Dispatchers.IO) {

                favorite = database.favoritesDao.getFavoriteByName(name)
            }

            return favorite
        }
    }

    //POKEDEX  -------------------------------------------------------------------------------------------

    class PokedexRepository(private val database: PocketdexDatabase) {
        val pokemonTypes: LiveData<List<DatabaseTypes>> = database.typesDao.getTypes()

        val pokemons: LiveData<List<DatabasePokemon>> = database.pokemonDao.getPokemons()

        suspend fun getTypes(appContext: Context) {
            var types: MutableList<DatabaseTypes>

            withContext(Dispatchers.IO) {
                types = database.typesDao.getTypesInRange(0, 100).toMutableList()

                if (types.isEmpty() && hasInternetConnection(appContext)) {

                    val typesGlobalData = PokeApi.retrofitService.getTypes(100, 0)

                    for (typeResult in typesGlobalData.results) {
                        val typeData = PokeApi.retrofitService.getTypeByName(typeResult.name)
                        val typeDatabase = typeData.asDatabaseModel()
                        types.add(typeDatabase)
                    }
                }

                database.typesDao.insertAll(types)
            }
        }

        suspend fun refreshTypes(appContext: Context) {
            if (!hasInternetConnection(appContext)) {
                return
            }
            withContext(Dispatchers.IO) {
                val typesGlobalData = PokeApi.retrofitService.getTypes(100, 0)

                val typesDataList = mutableListOf<DatabaseTypes>()

                for (typeResult in typesGlobalData.results) {
                    val typeData = PokeApi.retrofitService.getTypeByName(typeResult.name)
                    val typeDatabase = typeData.asDatabaseModel()
                    typesDataList.add(typeDatabase)
                }

                database.typesDao.insertAll(typesDataList)
            }
        }

        suspend fun getPokemons(appContext: Context, limit: Int, offset: Int) {
            if (!hasInternetConnection(appContext)) {
                return
            }

            var pokemons: MutableList<DatabasePokemon>

            withContext(Dispatchers.IO) {
                val max = offset + limit
                pokemons = database.pokemonDao.getPokemonsInRange(offset, max).toMutableList()

                if (pokemons.isEmpty() && hasInternetConnection(appContext)) {

                    val pokemonsGlobalData = PokeApi.retrofitService.getPokemons(limit, offset)

                    for (pokemonResult in pokemonsGlobalData.results) {
                        val pokemonDetailedData =
                            PokeApi.retrofitService.getPokemonByName(pokemonResult.name)

                        val databasePokemon = pokemonDetailedData.asDatabaseModel()

                        pokemons.add(databasePokemon)
                    }
                }

                database.pokemonDao.insertAll(pokemons)
            }
        }

        suspend fun refreshPokemons(appContext: Context, limit: Int, offset: Int) {
            if (!hasInternetConnection(appContext)) {
                return
            }
            withContext(Dispatchers.IO) {
                val pokemonsGlobalData = PokeApi.retrofitService.getPokemons(limit, offset)

                val pokemonsDataList = mutableListOf<DatabasePokemon>()

                for (pokemonResult in pokemonsGlobalData.results) {
                    val pokemonDetailedData =
                        PokeApi.retrofitService.getPokemonByName(pokemonResult.name)

                    val databasePokemon = pokemonDetailedData.asDatabaseModel()

                    pokemonsDataList.add(databasePokemon)
                }

                database.pokemonDao.insertAll(pokemonsDataList)
            }
        }

        suspend fun getTypeByName(appContext: Context, name: String): DatabaseTypes? {

            if (!hasInternetConnection(appContext)) {
                return null
            }

            var typeDatabase: DatabaseTypes?

            withContext(Dispatchers.IO) {

                typeDatabase = database.typesDao.getTypeByName(name)

                if (typeDatabase == null) {
                    val typeDetailedData = PokeApi.retrofitService.getTypeByName(name)

                    //pokeDatabase = PokemonApiToPokemonDatabase(pokemonDetailedData)
                    typeDatabase = typeDetailedData.asDatabaseModel()
                }
            }

            return typeDatabase

        }

        suspend fun getPokemonByName(appContext: Context, name: String): DatabasePokemon? {
            if (!hasInternetConnection(appContext)) {
                return null
            }
            var pokeDatabase: DatabasePokemon?

            withContext(Dispatchers.IO) {

                pokeDatabase = database.pokemonDao.getPokemonByName(name)

                if (pokeDatabase == null) {
                    val pokemonDetailedData = PokeApi.retrofitService.getPokemonByName(name)

                    pokeDatabase = pokemonDetailedData.asDatabaseModel()
                }
            }

            return pokeDatabase
        }
    }


    //ITEMS  -------------------------------------------------------------------------------------------


    class ItemsRepository(private val database: PocketdexDatabase) {
        val items: LiveData<List<DatabaseItems>> = database.itemsDao.getItems()

        val itemCategories: LiveData<List<DatabaseItemCategories>> =
            database.itemCategoriesDao.getItemCategories()

        suspend fun getItemCategories(appContext: Context, limit: Int, offset: Int) {
            if (!hasInternetConnection(appContext)) {
                return
            }

            var itemCategories: MutableList<DatabaseItemCategories>

            withContext(Dispatchers.IO) {
                val max = offset + limit
                itemCategories =
                    database.itemCategoriesDao.getItemCategoriesInRange(offset, max).toMutableList()

                if (itemCategories.isEmpty() && hasInternetConnection(appContext)) {

                    val itemCategoriesGlobalData =
                        PokeApi.retrofitService.getItemCategories(limit, offset)

                    for (itemCategoryResult in itemCategoriesGlobalData.results) {
                        val itemCategoryData =
                            PokeApi.retrofitService.getItemCategoryByName(itemCategoryResult.name)

                        val databaseItemCategory = itemCategoryData.asDatabaseModel()

                        itemCategories.add(databaseItemCategory)
                    }
                }

                database.itemCategoriesDao.insertAll(itemCategories)
            }
        }

        suspend fun refreshItemCategories(appContext: Context, limit: Int, offset: Int) {
            if (!hasInternetConnection(appContext)) {
                return
            }
            withContext(Dispatchers.IO) {
                val itemCategoriesData = PokeApi.retrofitService.getItemCategories(limit, offset)

                val itemCategoriesDataList = mutableListOf<DatabaseItemCategories>()

                for (itemCategoryResult in itemCategoriesData.results) {
                    val itemCategoryDetailedData =
                        PokeApi.retrofitService.getItemCategoryByName(itemCategoryResult.name)

                    val databaseItemCategory = itemCategoryDetailedData.asDatabaseModel()

                    itemCategoriesDataList.add(databaseItemCategory)
                }

                database.itemCategoriesDao.insertAll(itemCategoriesDataList)
            }
        }


    }

}