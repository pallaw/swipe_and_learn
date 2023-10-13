package com.pallaw.swipeandlearnf.domain.model

data class UserData(
    val streakCount: Int =0,
    val hintCount: Int = 0,
    val diamondCount: Int = 0,
    val skipCount: Int = 0,
    val rewards: List<Reward> = emptyList()
)