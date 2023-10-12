package com.pallaw.swipeandlearnf.data.model

data class UserDataDto(
    val streak_count: Int,
    val rewards: List<RewardDto>
)