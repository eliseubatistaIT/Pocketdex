package com.eliseubatista.pocketdex.database.items

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {
    @Query("select * from databaseitems")
    fun getItems(): LiveData<List<DatabaseItems>>

    @Query("select * from databaseitems where id > :minId and id <= :maxId")
    fun getItemsInRange(minId: Int, maxId: Int): List<DatabaseItems>

    @Query("select * from databaseitems where name = :name")
    fun getItemsByName(name: String): DatabaseItems?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: DatabaseItems)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<DatabaseItems>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: DatabaseItems)
}
