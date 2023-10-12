package com.pallaw.swipeandlearnf.domain.model

data class UserData(
    val streakCount: Int,
    val rewards: List<Reward> = emptyList()
)