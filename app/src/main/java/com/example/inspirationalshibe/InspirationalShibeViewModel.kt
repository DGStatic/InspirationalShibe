package com.example.inspirationalshibe

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class InspirationalShibeViewModel : ViewModel() {

    val shibeLiveData: LiveData<List<String>>
    val quoteLiveData: LiveData<List<Quote>>

    init {
        shibeLiveData = ShibeFetcher().fetchShibes()
        quoteLiveData = QuoteFetcher().fetchQuotes()
    }
}