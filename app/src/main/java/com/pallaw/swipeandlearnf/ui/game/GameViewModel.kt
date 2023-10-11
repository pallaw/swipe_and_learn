package com.pallaw.swipeandlearnf.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pallaw.swipeandlearnf.domain.GameRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GameViewModel(
    val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameContract.State())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<GameContract.Event>()
    val event = _event.asSharedFlow()

    private val _effect: Channel<GameContract.Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    private fun handleEvent(event: GameContract.Event) {
        when (event) {
            GameContract.Event.ChooseSubjectClicked -> TODO()
            GameContract.Event.GetQuestions -> TODO()
            GameContract.Event.HintClicked -> TODO()
            is GameContract.Event.QuestionSwiped -> TODO()
            GameContract.Event.RestartClicked -> TODO()
            GameContract.Event.RewardClicked -> {
                setEffect { GameContract.Effect.NavigateToRewards }
            }
            GameContract.Event.SkipClicked -> TODO()
        }
    }

    fun setEvent(event: GameContract.Event) = viewModelScope.launch { _event.emit(event) }

    private fun setState(reduce: GameContract.State.() -> GameContract.State) {
        _uiState.value = uiState.value.reduce()
    }

    private fun setEffect(effect: () -> GameContract.Effect) {
        viewModelScope.launch {
            _effect.send(effect())
        }
    }

}





