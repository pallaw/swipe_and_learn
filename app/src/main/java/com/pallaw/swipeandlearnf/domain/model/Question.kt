package com.pallaw.swipeandlearnf.domain.model

data class Question(
    val answer: Boolean?=null,
    val hint: String?="",
    val question: String?=""
)