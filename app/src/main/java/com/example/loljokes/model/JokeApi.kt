package com.example.loljokes.model

import retrofit2.Response
import retrofit2.http.GET

interface JokeApi {

    @GET("/random_ten")
    suspend fun getJokes(): Response<List<Joke>>
}