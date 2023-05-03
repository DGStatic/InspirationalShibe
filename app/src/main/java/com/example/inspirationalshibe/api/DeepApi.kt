package com.example.inspirationalshibe.api

import com.example.inspirationalshibe.DeepResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DeepApi {

    @GET(
        "v2/translate?auth_key=c2ee6f30-bdb3-a628-c564-ffd50aea50e1:fx"
    )
    fun fetchTranslated(@Query("target_lang") language: String, @Query("text") text: String): Call<DeepResponse>
}