package com.example.inspirationalshibe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.inspirationalshibe.api.DeepApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "DeepFetcher"

class DeepFetcher {

    private val deepApi: DeepApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api-free.deepl.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        deepApi = retrofit.create(DeepApi::class.java)
    }

    fun fetchTranslated(language: String, text: String): MutableLiveData<List<TranslatedText>> {
        var responseD : MutableLiveData<List<TranslatedText>> = MutableLiveData()
        val deepRequest: Call<DeepResponse> = deepApi.fetchTranslated(language, text)

        deepRequest.enqueue(object : Callback<DeepResponse> {

            override fun onFailure(call: Call<DeepResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch translated text", t)
            }

            override fun onResponse(
                call: Call<DeepResponse>,
                response: Response<DeepResponse>
            ) {
                Log.d(TAG, "Response received")
                val deepResponse: DeepResponse? = response.body()
                var translations: List<TranslatedText> = deepResponse?.translations ?: mutableListOf()

                responseD.value= translations
            }
        })

        return responseD
    }}