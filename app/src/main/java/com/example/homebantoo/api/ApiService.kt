package com.example.homebantoo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/search/")
    fun getSearch(
        @Query("q") value: String
    ): Call<SearchResponse>
}