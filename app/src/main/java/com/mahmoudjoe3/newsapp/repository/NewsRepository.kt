package com.mahmoudjoe3.newsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudjoe3.newsapp.api.NewsApi
import com.mahmoudjoe3.newsapp.pojo.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsApi: NewsApi){

    fun getArticles(): LiveData<List<Article>> {

        val articles: MutableLiveData<List<Article>> = MutableLiveData()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val news=newsApi.getArticles().articles
                withContext(Dispatchers.Main) {
                    articles.value = news
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main) {
                    articles.value = null
                }
                Log.d("TAG", "getArticles: "+ex.message)
            }
        }
        return articles
    }

}