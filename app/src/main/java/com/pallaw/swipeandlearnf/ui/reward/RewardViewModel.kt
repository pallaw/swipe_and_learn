package com.pallaw.swipeandlearnf.ui.reward

import androidx.lifecycle.viewModelScope
import com.pallaw.swipeandlearnf.base.BaseViewModel
import com.pallaw.swipeandlearnf.domain.GameRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RewardViewModel(
    val gameRepository: GameRepository
) : BaseViewModel<RewardScreenContract.State, RewardScreenContract.Event, RewardScreenContract.Effect>() {

    init {
        setEvent(RewardScreenContract.Event.GetUserRewards)
    }

    override fun createInitialState(): RewardScreenContract.State {
        return RewardScreenContract.State(loading = true)
    }

    override fun handleEvent(event: RewardScreenContract.Event) {
        when (event) {
            is RewardScreenContract.Event.GetUserRewards -> {
                viewModelScope.launch {
                    gameRepository.getUserRewards(1).collectLatest { rewards ->
                        setState {
                            this.copy(
                                loading = false,
                                rewards = rewards
                            )
                        }
                    }
                }
            }

            is RewardScreenContract.Event.RewardClicked -> {
                setEffect { RewardScreenContract.Effect.ShowRewardDialog(event.reward) }
            }
        }
    }
}