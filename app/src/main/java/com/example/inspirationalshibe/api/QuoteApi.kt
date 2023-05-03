package com.example.inspirationalshibe.api

import com.example.inspirationalshibe.Quote
import com.example.inspirationalshibe.QuoteResponse
import retrofit2.Call
import retrofit2.http.GET

interface QuoteApi {
    @GET(
        "api/quotes"
    )
    fun fetchQuotes(): Call<List<Quote>>

}