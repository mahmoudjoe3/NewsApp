package com.mahmoudjoe3.newsapp.api

import com.mahmoudjoe3.newsapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/
// top-headlines?
//country=eg
//	&apiKey=63b1f94dad044add871d1e319c630265
interface NewsApi {

    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = BuildConfig.NEWS_API_KEY
    }


    @GET("top-headlines")
    suspend fun getArticles(
        @Query("country") country:String = "eg",
        @Query("apiKey") apiKey:String = API_KEY
    ):NewsResponse

}