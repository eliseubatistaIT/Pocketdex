package com.eliseubatista.pocketdex.database.favorites

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eliseubatista.pocketdex.database.favorites.DatabaseFavorites

@Dao
interface FavoritesDao {

    @Query("select * from databasefavorites")
    fun getFavorites(): LiveData<List<DatabaseFavorites>>

    @Query("select * from databasefavorites where category = 'pokemon'")
    fun getFavoritePokemons(): LiveData<List<DatabaseFavorites>>

    @Query("select * from databasefavorites where category = 'item'")
    fun getFavoriteItems(): LiveData<List<DatabaseFavorites>>

    @Query("select * from databasefavorites where category = 'region'")
    fun getFavoriteRegions(): LiveData<List<DatabaseFavorites>>

    @Query("select * from databasefavorites where name = :name")
    fun getFavoriteByName(name: String): DatabaseFavorites?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg favorites: DatabaseFavorites)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(favorites: List<DatabaseFavorites>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: DatabaseFavorites)

    @Delete
    fun delete(favorite: DatabaseFavorites)
}
