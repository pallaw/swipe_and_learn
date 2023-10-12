package com.pallaw.swipeandlearnf.ui.reward

import com.pallaw.swipeandlearnf.domain.model.Reward

class RewardScreenContract {
    // View actions
    sealed class Event {
        object GetUserRewards : Event()
        data class RewardRevealed(val reward: Reward) : Event()
        data class RewardClicked(val reward: Reward) : Event()
    }

    // state for UI data
    data class State(
        val loading: Boolean = true,
        val rewards: List<Reward> = emptyList(),
    )

    // one time effects handled in UI
    sealed class Effect {
        data class ShowRewardDialog(val reward: Reward) : Effect()
        data class ShowMessage(val msg: String) : Effect()
    }
}