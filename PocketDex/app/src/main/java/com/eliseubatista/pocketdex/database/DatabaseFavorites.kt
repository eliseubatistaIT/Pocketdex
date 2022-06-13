package com.eliseubatista.pocketdex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseFavorites constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val category: String
)
