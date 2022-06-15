package com.reserach.newsapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reserach.newsapp.api.Api
import com.reserach.newsapp.data.model.NewsResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel: ViewModel(), CoroutineScope {

    private lateinit var service: Api
    val isSuccess = MutableLiveData<Boolean>()
    val listNews = MutableLiveData<List<NewsResponse.Articles>>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: Api) {
        this.service = service
    }

    fun callNewsApi(country: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getNews(country)
                } catch (e:Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isSuccess.value = false
                    }
                }
            }

            if (result is NewsResponse) {
                if (result.status.equals("ok")) {
                    isSuccess.value = true
                    listNews.value = result.articles
                }
            }
        }
    }
}