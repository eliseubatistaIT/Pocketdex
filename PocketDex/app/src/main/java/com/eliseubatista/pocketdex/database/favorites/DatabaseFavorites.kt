package com.eliseubatista.pocketdex.database.favorites

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eliseubatista.pocketdex.database.pokemons.DatabasePokemon
import com.eliseubatista.pocketdex.repository.PocketdexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Entity
data class DatabaseFavorites constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val category: String
)