package com.pallaw.swipeandlearnf.domain.model

data class Question(
    val id: Int = 0,
    val answer: Boolean=false,
    val hint: String="",
    val question: String=""
)