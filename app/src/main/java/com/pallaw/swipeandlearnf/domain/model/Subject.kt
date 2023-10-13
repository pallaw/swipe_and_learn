package com.pallaw.swipeandlearnf.domain.model

data class Subject(
    val _id: String="",
    val chapters: List<String> = emptyList(),
    val name: String = ""
)