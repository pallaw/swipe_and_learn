package com.pallaw.swipeandlearnf.domain

import com.pallaw.swipeandlearnf.data.model.QuestionDto
import com.pallaw.swipeandlearnf.data.model.RewardDto
import com.pallaw.swipeandlearnf.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getUserData(): Flow<UserData>
    fun getQuestions(): Flow<List<QuestionDto>>
    fun getRewards(): Flow<List<RewardDto>>
    fun getReward(streakCount: Int) : Flow<RewardDto>
}