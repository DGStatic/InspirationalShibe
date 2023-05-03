package com.example.inspirationalshibe.api

import retrofit2.Call
import retrofit2.http.GET

interface ShibeApi {
    @GET(
        "api/shibes?count=50"
    )
    fun fetchShibes(): Call<List<String>>

}