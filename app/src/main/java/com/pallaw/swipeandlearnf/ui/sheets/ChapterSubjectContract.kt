package com.pallaw.swipeandlearnf.ui.sheets

import com.pallaw.swipeandlearnf.domain.model.Subject

class ChapterSubjectContract {
    sealed class Event {
       object GetSubjectListing : Event()
    }

    data class State(
        val subjects: List<Subject> = emptyList()
    )

    sealed class Effect {}
}