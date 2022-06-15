package com.reserach.newsapp.data.model

data class NewsResponse(val status: String, val totalResults: Int, val articles: List<Articles>) {
    data class Articles(
        val author: String,
        val title: String,
        val urlToImage: String,
        val publishedAt: String,
        val content: String
    )
}
