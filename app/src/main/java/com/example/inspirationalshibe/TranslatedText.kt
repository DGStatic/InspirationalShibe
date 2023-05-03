package com.example.inspirationalshibe

import com.google.gson.annotations.SerializedName

data class TranslatedText (
    @SerializedName("text") var text: String = ""
        )