package com.pallaw.swipeandlearnf.ui.game

import androidx.lifecycle.viewModelScope
import com.pallaw.swipeandlearnf.base.BaseViewModel
import com.pallaw.swipeandlearnf.domain.GameRepository
import com.pallaw.swipeandlearnf.domain.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel(
    val gameRepository: GameRepository
) : BaseViewModel<GameScreenContract.State, GameScreenContract.Event, GameScreenContract.Effect>() {

    init {
        setEvent(GameScreenContract.Event.GetUserData)
        setEvent(GameScreenContract.Event.GetQuestions)
    }

    override fun createInitialState(): GameScreenContract.State {
        return GameScreenContract.State(loading = true)
    }

    override fun handleEvent(event: GameScreenContract.Event) {
        when (event) {
            GameScreenContract.Event.ChooseSubjectClicked -> TODO()
            GameScreenContract.Event.GetQuestions -> {
                viewModelScope.launch(Dispatchers.IO) {
                    gameRepository.getQuestions().collectLatest { questions ->
                        withContext(Dispatchers.Main) {
                            setState {
                                this.copy(
                                    loading = false,
                                    questions = questions
                                )
                            }
                        }
                    }
                }
            }

            is GameScreenContract.Event.QuestionSwiped -> TODO()
            GameScreenContract.Event.ResetGame -> {
                val questions = uiState.value.questions
                val newQuestions = arrayListOf<Question>()
                newQuestions.addAll(questions)
                setState {
                    this.copy(
                        streakCount = 0,
                        questions = newQuestions
                    )
                }
            }
            GameScreenContract.Event.RewardClicked -> {
                setEffect { GameScreenContract.Effect.NavigateToRewards }
            }

            GameScreenContract.Event.GetUserData -> {
                viewModelScope.launch(Dispatchers.IO) {
                    gameRepository.getUserData().collectLatest { userData ->
                        withContext(Dispatchers.Main) {
                            setState {
                                this.copy(
                                    loading = false,
                                    diamondsCount = userData.diamondCount,
                                    hintCount = userData.hintCount,
                                    skipCount = userData.skipCount,
                                    streakCount = userData.streakCount
                                )
                            }
                        }
                    }
                }
            }

            is GameScreenContract.Event.SubmitAnswer -> {
                val position = event.position
                val answer = event.answer

                val question = uiState.value.questions[position]
                if (question.answer == answer) {
                    setEffect { GameScreenContract.Effect.ShowMsg("Answer is correct , streak increased") }
                    val newStreakCount = uiState.value.streakCount + 1
                    setState {
                        this.copy(
                            streakCount = newStreakCount
                        )
                    }
                } else {
                    setEffect { GameScreenContract.Effect.ShowMsg("Wrong answer, Game Over") }
                    setEffect { GameScreenContract.Effect.GameOver }
                }
            }

            is GameScreenContract.Event.HintClicked -> {
                val question = uiState.value.questions[event.currentPosition]
                var hintCount = uiState.value.hintCount
                if (hintCount > 0) {
                    setEffect { GameScreenContract.Effect.ShowMsg("Hint: ${question.hint}") }
                    setState {
                        this.copy(
                            hintCount = --hintCount
                        )
                    }
                } else {
                    setEffect { GameScreenContract.Effect.ShowMsg("You are out of hint") }
                }
            }

            is GameScreenContract.Event.SkipClicked -> {
                var skipCount = uiState.value.skipCount
                if (skipCount > 0) {
                    setEffect { GameScreenContract.Effect.SkipQuestion }
                    setEffect { GameScreenContract.Effect.ShowMsg("Question skipped") }
                    setState { this.copy(skipCount = --skipCount) }
                } else {
                    setEffect { GameScreenContract.Effect.ShowMsg("You are out of skips") }
                }
            }
        }
    }


}





