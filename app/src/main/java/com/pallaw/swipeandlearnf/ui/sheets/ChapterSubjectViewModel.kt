package com.pallaw.swipeandlearnf.ui.sheets

import androidx.lifecycle.viewModelScope
import com.pallaw.swipeandlearnf.base.BaseViewModel
import com.pallaw.swipeandlearnf.domain.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChapterSubjectViewModel(
    val gameRepository: GameRepository
): BaseViewModel<ChapterSubjectContract.State,
        ChapterSubjectContract.Event,
        ChapterSubjectContract.Effect> (){


    init {
        setEvent(
            ChapterSubjectContract.Event.GetSubjectListing
        )
    }

    override fun createInitialState(): ChapterSubjectContract.State {
        return ChapterSubjectContract.State()
    }

    override fun handleEvent(event: ChapterSubjectContract.Event) {
        when(event) {
            ChapterSubjectContract.Event.GetSubjectListing -> {
                 viewModelScope.launch {
                    gameRepository.getSubjectsAndChapters().collectLatest { questions ->
                        withContext(Dispatchers.Main) {
                            setState {
                                this.copy(
                                    subjects = questions
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}