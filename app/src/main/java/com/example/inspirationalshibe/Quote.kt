package com.example.inspirationalshibe

import com.google.gson.annotations.SerializedName

data class Quote (
    @SerializedName("q") var quote: String = ""
        )