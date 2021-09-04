package com.mahmoudjoe3.newsapp.ui.news_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudjoe3.newsapp.pojo.Article
import com.mahmoudjoe3.newsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var articles = newsRepository.getArticles()

    fun refreshArticles(){
        articles = newsRepository.getArticles()
    }
}