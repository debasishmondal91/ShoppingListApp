package com.example.shoppinglistapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingItems(items: ShoppingItems)

    @Delete
    suspend fun deleteItems(items: ShoppingItems)

    @Query("SELECT * from shopping_items")
    fun observeAllItems(): LiveData<List<ShoppingItems>>

    @Query("SELECT SUM(price * amount) from shopping_items")
    fun observeTotalPrice(): LiveData<Float>

}