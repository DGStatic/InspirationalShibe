package com.example.inspirationalshibe

import com.google.gson.annotations.SerializedName

class DeepResponse {
    @SerializedName("translations") lateinit var translations: List<TranslatedText>
}