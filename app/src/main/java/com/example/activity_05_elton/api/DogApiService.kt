package com.example.activity_05_elton.api

import com.example.activity_05_elton.model.BreedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DogApiService {

    //get all breeds
    @GET("breeds")
    fun getBreeds():Call<BreedResponse>
}