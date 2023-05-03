package com.example.inspirationalshibe

import com.google.gson.annotations.SerializedName

class TranslationResponse {
    @SerializedName("translations") lateinit var translations: List<TranslatedText>
}