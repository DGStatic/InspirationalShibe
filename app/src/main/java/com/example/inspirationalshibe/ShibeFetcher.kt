package com.example.inspirationalshibe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inspirationalshibe.api.ShibeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "ShibeFetcher"

class ShibeFetcher {

    private val shibeApi: ShibeApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://shibe.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        shibeApi = retrofit.create(ShibeApi::class.java)
    }

    fun fetchShibes(): MutableLiveData<List<String>> {
        val responseLiveData: MutableLiveData<List<String>> = MutableLiveData()
        val shibeRequest: Call<List<String>> = shibeApi.fetchShibes()

        shibeRequest.enqueue(object : Callback<List<String>> {

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.e(TAG, "Failed to fetch shibe", t)
            }

            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                Log.d(TAG, "Response received")
                val shibeResponse: List<String>? = response.body()
                var shibes: List<String> = shibeResponse
                    ?: mutableListOf()
                shibes = shibes.filterNot {
                    it.isBlank()
                }
                responseLiveData.value = shibes
            }
        })

        return responseLiveData
    }}