package com.pallaw.swipeandlearnf.ui.game

import com.pallaw.swipeandlearnf.data.model.QuestionDto

class GameContract {

    // View actions
    public sealed class Event {
        object GetQuestions : Event()
        class QuestionSwiped(val response: Boolean) : Event()
        object HintClicked : Event()
        object SkipClicked : Event()
        object RestartClicked : Event()
        object ChooseSubjectClicked : Event()
        object RewardClicked : Event()

    }

    // state for UI data
    data class State(
        val loading: Boolean = true,
        val questions: List<QuestionDto> = emptyList(),
        val hintCount: Int = 0,
        val skipCount: Int = 0,
        val streakCount: Int = 0,
        val diamondsCount: Int = 0,
        val enableCountDownTimer: Boolean = false,
        val countDownTimerValue: Int = 0,
        val showRewards: Boolean = false,
    )

    // one time effects handled in UI
    sealed class Effect {
        object NavigateToRewards : Effect()
    }
}