package com.pallaw.swipeandlearnf.di

import com.pallaw.swipeandlearnf.ui.game.GameViewModel
import com.pallaw.swipeandlearnf.ui.reward.RewardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<GameViewModel> {
        GameViewModel(
            gameRepository = get()
        )
    }

    viewModel<RewardViewModel> {
        RewardViewModel(
            gameRepository = get()
        )
    }
}