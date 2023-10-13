package com.pallaw.swipeandlearnf.ui.game

import com.pallaw.swipeandlearnf.domain.model.Question

class GameScreenContract {

    // View actions
    public sealed class Event {
        object GetUserData : Event()
        object GetQuestions : Event()
        class QuestionSwiped(val response: Boolean) : Event()
        data class HintClicked(val currentPosition: Int) : Event()
        data class SkipClicked(val currentPosition: Int) : Event()
        object ResetGame : Event()
        object ChooseSubjectClicked : Event()
        object RewardClicked : Event()
        data class SubmitAnswer(val position: Int, val answer: Boolean) : Event()

    }

    // state for UI data
    data class State(
        val loading: Boolean = true,
        val questions: List<Question> = emptyList(),
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
        object NavigateToGameOver: Effect()
        object GameOver: Effect()
        object SkipQuestion: Effect()
        data class ShowMsg(val msg: String): Effect()
    }
}