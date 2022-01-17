package com.example.shoppinglistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItems::class], version = 1)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

}