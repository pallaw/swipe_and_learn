package com.pallaw.swipeandlearnf.domain.mapper

import com.pallaw.swipeandlearnf.data.model.QuestionDto
import com.pallaw.swipeandlearnf.domain.model.Question

fun QuestionDto.toQuestion(): Question {
    return Question(
        answer = this.answer,
        question = this.question,
        hint = this.hint
    )
}