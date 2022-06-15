package com.reserach.newsapp.api

import com.reserach.newsapp.data.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String
    ): NewsResponse
}