package com.eliseubatista.pocketdex.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.eliseubatista.pocketdex.database.PocketdexDatabase
import com.eliseubatista.pocketdex.database.favorites.DatabaseFavorites
import com.eliseubatista.pocketdex.database.items.DatabaseItemCategories
import com.eliseubatista.pocketdex.database.items.DatabaseItems
import com.eliseubatista.pocketdex.database.regions.DatabaseLocation
import com.eliseubatista.pocketdex.database.regions.DatabaseRegions
import com.eliseubatista.pocketdex.database.pokemons.DatabasePokemon
import com.eliseubatista.pocketdex.database.pokemons.DatabaseTypes
import com.eliseubatista.pocketdex.network.PokeApi
import com.eliseubatista.pocketdex.network.items.asDatabaseModel
import com.eliseubatista.pocketdex.network.regions.asDatabaseModel
import com.eliseubatista.pocketdex.network.pokemons.asDatabaseModel
import com.eliseubatista.pocketdex.utils.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PocketdexRepository(database: PocketdexDatabase) {

    val favoritesRepository = FavoriteRepository(database)
    val pokedexRepository = PokedexRepository(database)
    val itemsRepository = ItemsRepository(database)
    val regionsRepository = RegionsRepository(database)

    //FAVORITES  -------------------------------------------------------------------------------------------

    class FavoriteRepository(private val database: PocketdexDatabase) {
        val favoritePokemons: LiveData<List<DatabaseFavorites>> =
            database.favoritesDao.getFavoritePokemons()

        val favoriteItems: LiveData<List<DatabaseFavorites>> =
            database.favoritesDao.getFavoriteItems()

        val favoriteLocations: LiveData<List<DatabaseFavorites>> =
            database.favoritesDao.getFavoriteRegions()

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

        suspend fun getPokemons(appContext: Context, limit: Int, offset: Int): Boolean {
            var pokemons: MutableList<DatabasePokemon>

            var resultsFound = false

            withContext(Dispatchers.IO) {
                val max = offset + limit
                pokemons = database.pokemonDao.getPokemonsInRange(offset, max).toMutableList()

                if (pokemons.isEmpty() && hasInternetConnection(appContext)) {

                    val pokemonsGlobalData = PokeApi.retrofitService.getPokemons(limit, offset)

                    resultsFound = pokemonsGlobalData.numberOfResults != 0

                    for (pokemonResult in pokemonsGlobalData.results) {
                        val pokemonDetailedData =
                            PokeApi.retrofitService.getPokemonByName(pokemonResult.name)

                        val databasePokemon = pokemonDetailedData.asDatabaseModel()

                        pokemons.add(databasePokemon)
                    }
                }

                database.pokemonDao.insertAll(pokemons)
            }

            return resultsFound
        }

        suspend fun getPokemonsLikeName(
            appContext: Context,
            name: String,
            locallyOnly: Boolean
        ): List<DatabasePokemon> {
            var pokemons: MutableList<DatabasePokemon>

            withContext(Dispatchers.IO) {
                pokemons = database.pokemonDao.getPokemonsByName(name).toMutableList()

                if (pokemons.isEmpty() && hasInternetConnection(appContext) && !locallyOnly) {

                    val pokemonsGlobalData = PokeApi.retrofitService.getPokemons(5000, 0)

                    for (pokemonResult in pokemonsGlobalData.results) {
                        if (!pokemonResult.name.contains(name)) {
                            continue
                        }

                        val pokemonDetailedData =
                            PokeApi.retrofitService.getPokemonByName(pokemonResult.name)

                        val databasePokemon = pokemonDetailedData.asDatabaseModel()

                        pokemons.add(databasePokemon)
                    }
                }

                database.pokemonDao.insertAll(pokemons)
            }

            return pokemons
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

        suspend fun getItemCategories(appContext: Context) {
            var itemCategories: MutableList<DatabaseItemCategories>

            withContext(Dispatchers.IO) {
                itemCategories =
                    database.itemCategoriesDao.getItemCategoriesInRange(0, 100).toMutableList()

                if (itemCategories.isEmpty() && hasInternetConnection(appContext)) {

                    val categoriesGlobalData = PokeApi.retrofitService.getItemCategories(100, 0)

                    for (categoryResult in categoriesGlobalData.results) {
                        val categoryData =
                            PokeApi.retrofitService.getItemCategoryByName(categoryResult.name)
                        val categoryDatabase = categoryData.asDatabaseModel()
                        itemCategories.add(categoryDatabase)
                    }
                }

                database.itemCategoriesDao.insertAll(itemCategories)
            }
        }

        suspend fun refreshItemCategories(appContext: Context) {
            if (!hasInternetConnection(appContext)) {
                return
            }
            withContext(Dispatchers.IO) {
                val categoriesGlobalData = PokeApi.retrofitService.getItemCategories(100, 0)

                val categoriesDataList = mutableListOf<DatabaseItemCategories>()

                for (categoryResult in categoriesGlobalData.results) {
                    val categoryData =
                        PokeApi.retrofitService.getItemCategoryByName(categoryResult.name)
                    val categoryDatabase = categoryData.asDatabaseModel()
                    categoriesDataList.add(categoryDatabase)
                }

                database.itemCategoriesDao.insertAll(categoriesDataList)
            }
        }

        suspend fun getItems(appContext: Context, limit: Int, offset: Int): Boolean {
            var items: MutableList<DatabaseItems>

            var resultsFound = false

            withContext(Dispatchers.IO) {
                val max = offset + limit
                items = database.itemsDao.getItemsInRange(offset, max).toMutableList()

                if (items.isEmpty() && hasInternetConnection(appContext)) {

                    val itemsGlobalData = PokeApi.retrofitService.getItems(limit, offset)

                    resultsFound = itemsGlobalData.numberOfResults != 0

                    for (itemResult in itemsGlobalData.results) {
                        val itemDetailedData =
                            PokeApi.retrofitService.getItemByName(itemResult.name)

                        val databaseItem = itemDetailedData.asDatabaseModel()

                        items.add(databaseItem)
                    }
                }

                database.itemsDao.insertAll(items)
            }

            return resultsFound
        }

        suspend fun getItemsLikeName(
            appContext: Context,
            name: String,
            locallyOnly: Boolean
        ): List<DatabaseItems> {
            var items: MutableList<DatabaseItems>

            withContext(Dispatchers.IO) {
                items = database.itemsDao.getItemsByName(name).toMutableList()

                if (items.isEmpty() && hasInternetConnection(appContext) && !locallyOnly) {

                    val itemsGlobalData = PokeApi.retrofitService.getItems(5000, 0)

                    for (itemsResult in itemsGlobalData.results) {
                        if (!itemsResult.name.contains(name)) {
                            continue
                        }

                        val itemDetailedData =
                            PokeApi.retrofitService.getItemByName(itemsResult.name)

                        val databaseItem = itemDetailedData.asDatabaseModel()

                        items.add(databaseItem)
                    }
                }

                database.itemsDao.insertAll(items)
            }

            return items
        }

        suspend fun refreshItems(appContext: Context, limit: Int, offset: Int) {
            if (!hasInternetConnection(appContext)) {
                return
            }
            withContext(Dispatchers.IO) {
                val itemsGlobalData = PokeApi.retrofitService.getItems(limit, offset)

                val itemsDataList = mutableListOf<DatabaseItems>()

                for (itemResult in itemsGlobalData.results) {
                    val itemDetailedData =
                        PokeApi.retrofitService.getItemByName(itemResult.name)

                    val databaseItem = itemDetailedData.asDatabaseModel()

                    itemsDataList.add(databaseItem)
                }

                database.itemsDao.insertAll(itemsDataList)
            }
        }

        suspend fun getItemCategoryByName(
            appContext: Context,
            name: String
        ): DatabaseItemCategories? {

            if (!hasInternetConnection(appContext)) {
                return null
            }

            var categoryDatabase: DatabaseItemCategories?

            withContext(Dispatchers.IO) {

                categoryDatabase = database.itemCategoriesDao.getItemCategoryByName(name)

                if (categoryDatabase == null) {
                    val categoryDetailedData = PokeApi.retrofitService.getItemCategoryByName(name)

                    categoryDatabase = categoryDetailedData.asDatabaseModel()
                }
            }

            return categoryDatabase

        }

        suspend fun getItemByName(appContext: Context, name: String): DatabaseItems? {
            if (!hasInternetConnection(appContext)) {
                return null
            }
            var itemDatabase: DatabaseItems?

            withContext(Dispatchers.IO) {

                itemDatabase = database.itemsDao.getItemByName(name)

                if (itemDatabase == null) {
                    val itemDetailedData = PokeApi.retrofitService.getItemByName(name)

                    itemDatabase = itemDetailedData.asDatabaseModel()
                }
            }

            return itemDatabase
        }


    }

    //Locations  -------------------------------------------------------------------------------------------

    class RegionsRepository(private val database: PocketdexDatabase) {
        val regions: LiveData<List<DatabaseRegions>> = database.regionDao.getRegions()

        val locations: LiveData<List<DatabaseLocation>> = database.locationDao.getLocations()

        suspend fun getRegions(appContext: Context, limit: Int, offset: Int): Boolean {
            var regions: MutableList<DatabaseRegions>

            var resultsFound = false

            withContext(Dispatchers.IO) {
                val max = offset + limit
                regions = database.regionDao.getRegionsInRange(offset, max).toMutableList()

                if (regions.isEmpty() && hasInternetConnection(appContext)) {

                    val regionsGlobalData = PokeApi.retrofitService.getRegions(limit, offset)

                    resultsFound = regionsGlobalData.numberOfResults != 0

                    for (regionResult in regionsGlobalData.results) {
                        val regionDetailedData =
                            PokeApi.retrofitService.getRegionByName(regionResult.name)

                        val databaseRegion = regionDetailedData.asDatabaseModel()

                        regions.add(databaseRegion)
                    }
                }

                database.regionDao.insertAll(regions)
            }

            return resultsFound
        }

        suspend fun getRegionsLikeName(
            appContext: Context,
            name: String,
            locallyOnly: Boolean
        ): List<DatabaseRegions> {
            var regions: MutableList<DatabaseRegions>

            withContext(Dispatchers.IO) {
                regions = database.regionDao.getRegionsByName(name).toMutableList()

                if (regions.isEmpty() && hasInternetConnection(appContext) && !locallyOnly) {

                    val regionsGlobalData = PokeApi.retrofitService.getLocations(5000, 0)

                    for (regionResult in regionsGlobalData.results) {
                        if (!regionResult.name.contains(name)) {
                            continue
                        }

                        val regionDetailedData =
                            PokeApi.retrofitService.getRegionByName(regionResult.name)

                        val databaseRegion = regionDetailedData.asDatabaseModel()

                        regions.add(databaseRegion)
                    }
                }

                database.regionDao.insertAll(regions)
            }

            return regions
        }

        suspend fun refreshRegions(appContext: Context, limit: Int, offset: Int) {
            if (!hasInternetConnection(appContext)) {
                return
            }
            withContext(Dispatchers.IO) {
                val regionsGlobalData = PokeApi.retrofitService.getRegions(limit, offset)

                val regionsDataList = mutableListOf<DatabaseRegions>()

                for (regionResult in regionsGlobalData.results) {
                    val regionDetailedData =
                        PokeApi.retrofitService.getRegionByName(regionResult.name)

                    val databaseRegion = regionDetailedData.asDatabaseModel()

                    regionsDataList.add(databaseRegion)
                }

                database.regionDao.insertAll(regionsDataList)
            }
        }

        suspend fun getLocations(appContext: Context) {
            var locations: MutableList<DatabaseLocation>

            withContext(Dispatchers.IO) {
                locations = database.locationDao.getLocationsInRange(0, 100).toMutableList()

                if (locations.isEmpty() && hasInternetConnection(appContext)) {

                    val locationsGlobalData = PokeApi.retrofitService.getLocations(100, 0)

                    for (locationResult in locationsGlobalData.results) {
                        val locationData =
                            PokeApi.retrofitService.getLocationByName(locationResult.name)
                        val locationDatabase = locationData.asDatabaseModel()
                        locations.add(locationDatabase)
                    }
                }

                database.locationDao.insertAll(locations)
            }
        }

        suspend fun refreshLocations(appContext: Context, limit: Int, offset: Int) {
            if (!hasInternetConnection(appContext)) {
                return
            }
            withContext(Dispatchers.IO) {
                val locationsGlobalData = PokeApi.retrofitService.getLocations(limit, offset)

                val locationsDataList = mutableListOf<DatabaseLocation>()

                for (locationResult in locationsGlobalData.results) {
                    val locationData =
                        PokeApi.retrofitService.getLocationByName(locationResult.name)
                    val locationDatabase = locationData.asDatabaseModel()
                    locationsDataList.add(locationDatabase)
                }

                database.locationDao.insertAll(locationsDataList)
            }
        }

        suspend fun getLocationByName(appContext: Context, name: String): DatabaseLocation? {
            if (!hasInternetConnection(appContext)) {
                return null
            }
            var locationDatabase: DatabaseLocation?

            withContext(Dispatchers.IO) {

                locationDatabase = database.locationDao.getLocationByName(name)

                if (locationDatabase == null) {
                    val locationDetailedData = PokeApi.retrofitService.getLocationByName(name)

                    locationDatabase = locationDetailedData.asDatabaseModel()
                }
            }

            return locationDatabase
        }

        suspend fun getRegionByName(appContext: Context, name: String): DatabaseRegions? {

            if (!hasInternetConnection(appContext)) {
                return null
            }

            var regionDatabase: DatabaseRegions?

            withContext(Dispatchers.IO) {

                regionDatabase = database.regionDao.getRegionByName(name)

                if (regionDatabase == null) {
                    val regionDetailedData = PokeApi.retrofitService.getRegionByName(name)

                    regionDatabase = regionDetailedData.asDatabaseModel()
                }
            }

            return regionDatabase

        }
    }

}