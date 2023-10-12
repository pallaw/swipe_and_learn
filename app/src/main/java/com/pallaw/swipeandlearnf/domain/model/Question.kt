package com.pallaw.swipeandlearnf.domain.model

data class Question(
    val answer: Boolean=false,
    val hint: String="",
    val question: String=""
)