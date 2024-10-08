package com.example.activity_05_elton.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DogApi {

    //Dog API URL
    private const val BASE_URL = "https://dogapi.dog/api/v2/"

    // Create and configure the Retrofit instance
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}