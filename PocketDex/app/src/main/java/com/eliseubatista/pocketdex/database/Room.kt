package com.eliseubatista.pocketdex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabasePokemon::class, DatabaseTypes::class, DatabaseFavorites::class], version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class PocketdexDatabase : RoomDatabase() {
    abstract val pokemonDao : PokemonDao
    abstract val typesDao : TypesDao
    abstract val favoritesDao: FavoritesDao
}

private lateinit var INSTANCE: PocketdexDatabase

fun getDatabase(context: Context): PocketdexDatabase {
    synchronized(PocketdexDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PocketdexDatabase::class.java,
                "pocketdex"
            ).build()
        }
    }
    return INSTANCE
}