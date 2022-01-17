package com.example.shoppinglistapp.data.remote

import com.example.shoppinglistapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun getImages(
        @Query("q") search: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponses>
}