package com.example.sinauopencvkotlin.data

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?
)
