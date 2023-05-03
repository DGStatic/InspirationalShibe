package com.example.inspirationalshibe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.inspirationalshibe.api.QuoteApi
import com.example.inspirationalshibe.api.ShibeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "QuoteFetcher"

class QuoteFetcher {

    private val quoteApi: QuoteApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        quoteApi = retrofit.create(QuoteApi::class.java)
    }

    fun fetchQuotes(): MutableLiveData<List<Quote>> {
        val responseLiveData: MutableLiveData<List<Quote>> = MutableLiveData()
        val quoteRequest: Call<List<Quote>> = quoteApi.fetchQuotes()

        quoteRequest.enqueue(object : Callback<List<Quote>> {

            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                Log.e(TAG, "Failed to fetch quote", t)
            }

            override fun onResponse(
                call: Call<List<Quote>>,
                response: Response<List<Quote>>
            ) {
                Log.d(TAG, "Response received")
                val quoteResponse: List<Quote>? = response.body()
                var quotes: List<Quote> = quoteResponse
                    ?: mutableListOf()
                quotes = quotes.filterNot {
                    it.quote.isBlank()
                }
                responseLiveData.value = quotes
            }
        })

        return responseLiveData
    }}