package com.pallaw.swipeandlearnf.data.model

data class SubjectDto(
    val name: String,
    val chapters: List<String> = listOf()
)