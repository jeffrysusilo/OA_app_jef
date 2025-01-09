package com.example.sinauopencvkotlin.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    fun getOsteoarthritisNews(
        @Query("q") query: String = "osteoarthritis",
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}
