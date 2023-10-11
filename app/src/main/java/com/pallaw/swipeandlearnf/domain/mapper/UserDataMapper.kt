package com.pallaw.swipeandlearnf.domain.mapper

import com.pallaw.swipeandlearnf.data.model.UserDataDto
import com.pallaw.swipeandlearnf.domain.model.UserData

fun UserDataDto.toUserData(): UserData = UserData(
    streakCount = this.streak_count,
    rewards = this.rewards.map { it.toReward() }
)