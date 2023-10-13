package com.pallaw.swipeandlearnf.domain.mapper

import com.pallaw.swipeandlearnf.data.model.SubjectDto
import com.pallaw.swipeandlearnf.domain.model.Subject

fun SubjectDto.toSubject(): Subject {
    return Subject(
        _id = this._id,
        chapters = this.chapters,
        name = this.name
    )
}