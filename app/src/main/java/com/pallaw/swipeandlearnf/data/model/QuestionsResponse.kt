package com.pallaw.swipeandlearnf.data.model


import com.google.gson.annotations.SerializedName

internal data class QuestionsResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("data")
    val `data`: List<QuestionDto>
)