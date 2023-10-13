package com.pallaw.swipeandlearnf.ui.game

import androidx.lifecycle.viewModelScope
import com.pallaw.swipeandlearnf.base.BaseViewModel
import com.pallaw.swipeandlearnf.domain.GameRepository
import com.pallaw.swipeandlearnf.domain.model.Reward
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

            GameScreenContract.Event.HintClicked -> TODO()
            is GameScreenContract.Event.QuestionSwiped -> TODO()
            GameScreenContract.Event.RestartClicked -> TODO()
            GameScreenContract.Event.RewardClicked -> {
                setEffect { GameScreenContract.Effect.NavigateToRewards }
            }

            GameScreenContract.Event.SkipClicked -> TODO()
            GameScreenContract.Event.GetUserData -> {
                viewModelScope.launch(Dispatchers.IO) {
                    gameRepository.getUserData().collectLatest { userData ->
                        withContext(Dispatchers.Main) {
                            setState {
                                this.copy(
                                    loading = false,
                                    diamondsCount = userData.rewards.filter { it.type == Reward.Type.DIAMOND }
                                        .sumOf { it.count },
                                    streakCount = userData.streakCount
                                )
                            }
                        }
                    }
                }
            }

        }
    }


}





