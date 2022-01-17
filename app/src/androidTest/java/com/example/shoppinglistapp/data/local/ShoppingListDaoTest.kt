package com.example.shoppinglistapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingListDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var shoppingListDatabase: ShoppingListDatabase
    private lateinit var shoppingListDao: ShoppingListDao


    @Before
    fun setUp() {
        shoppingListDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingListDatabase::class.java
        ).allowMainThreadQueries().build()

        shoppingListDao = shoppingListDatabase.shoppingListDao()
    }

    @After
    fun tearDown() {
        shoppingListDatabase.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {

        val shoppingItem = ShoppingItems("Banana", 10, 150F, "yeruyreu", 1)
        shoppingListDao.addShoppingItems(shoppingItem)

        val allShoppingItems = shoppingListDao.observeAllItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItems() = runBlockingTest {
        val shoppingItems = ShoppingItems("Banana", 10, 150F, "yeruyreu", 1)
        shoppingListDao.deleteItems(shoppingItems)

        val allShoppingItems = shoppingListDao.observeAllItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItems)
    }

    @Test
    fun observeTotalPrice() = runBlockingTest {
        val shoppingItems1 = ShoppingItems("Banana", 10, 5.5F, "yeruyreu", 1)
        val shoppingItems2 = ShoppingItems("Banana", 5, 10.0F, "yeruyreu", 2)
        val shoppingItems3 = ShoppingItems("Banana", 6, 8.0F, "yeruyreu", 3)

        shoppingListDao.addShoppingItems(shoppingItems1)
        shoppingListDao.addShoppingItems(shoppingItems2)
        shoppingListDao.addShoppingItems(shoppingItems3)

        val totalPriceSum = shoppingListDao.observeTotalPrice().getOrAwaitValue()

        println("$totalPriceSum")

        assertThat(totalPriceSum).isEqualTo(10 * 5.5f + 5 * 10.0f + 6 * 8.0f)
    }
}