package com.example.sinauopencvkotlin.auth

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sinauopencvkotlin.R
import com.example.sinauopencvkotlin.data.NewsResponse
import com.example.sinauopencvkotlin.data.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val apiKey = "5e2a8e86b1984218afbd837608c6ca60"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        recyclerView = findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchNews()
    }

    private fun fetchNews() {
        RetrofitInstance.api.getOsteoarthritisNews(apiKey = apiKey)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val articles = response.body()!!.articles
                        newsAdapter = NewsAdapter(articles)
                        recyclerView.adapter = newsAdapter
                    } else {
                        Toast.makeText(this@NewsActivity, "Failed to load news", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.e("NewsActivity", "Error fetching news", t)
                    Toast.makeText(this@NewsActivity, "Error fetching news", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
