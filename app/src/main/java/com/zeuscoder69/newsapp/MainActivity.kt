package com.zeuscoder69.newsapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), itemClicked {

    private lateinit var mAdapter: newsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val mRecyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        mRecyclerView.layoutManager= LinearLayoutManager(this)

        fetchData()
        mAdapter = newsAdapter(this)
        mRecyclerView.adapter=mAdapter
    }

    private fun fetchData()
    {
     val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        //val url = "https://newsapi.org/v2/everything?q=tesla&from=2021-02-23&sortBy=publishedAt&apiKey=93a328dcace841f1a8014d3fd624c6bf"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<news>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val newsObj = news(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(newsObj)
                }
                mAdapter.newsUpdate(newsArray)


            },
            Response.ErrorListener {
                //Log.d("NewsError",it.localizedMessage)
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }

        )
        newsApiCall.getInstance(this).addToRequestQueue(jsonObjectRequest)



    }

    override fun onItemClicked(item: news) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}