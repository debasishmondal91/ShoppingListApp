package com.example.shoppinglistapp.di

import android.content.Context
import androidx.room.Room
import com.example.shoppinglistapp.data.local.ShoppingListDatabase
import com.example.shoppinglistapp.data.remote.PixabayAPI
import com.example.shoppinglistapp.data.utils.Constants.BASE_URL
import com.example.shoppinglistapp.data.utils.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesShoppingListDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ShoppingListDatabase::class.java,
        DB_NAME
    )

    @Provides
    @Singleton
    fun providesShoppingDao(
        database: ShoppingListDatabase
    ) = database.shoppingListDao()

    @Provides
    @Singleton
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}