package com.pallaw.swipeandlearnf.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pallaw.swipeandlearnf.domain.GameRepository
import com.pallaw.swipeandlearnf.domain.model.Reward
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<STATE, EVENT, EFFECT> : ViewModel() {

    private val _uiState = MutableStateFlow(createInitialState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<EVENT>()
    private val event = _event.asSharedFlow()

    private val _effect: Channel<EFFECT> = Channel()
    val effect = _effect.receiveAsFlow()

    abstract fun createInitialState(): STATE
    
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

    abstract fun handleEvent(event: EVENT) 

    fun setEvent(event: EVENT) = viewModelScope.launch { _event.emit(event) }

    fun setState(reduce: STATE.() -> STATE) {
        _uiState.value = uiState.value.reduce()
    }

    fun setEffect(effect: () -> EFFECT) {
        viewModelScope.launch {
            _effect.send(effect())
        }
    }

}





