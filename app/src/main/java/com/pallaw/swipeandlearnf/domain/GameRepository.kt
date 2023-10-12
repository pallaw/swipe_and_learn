package com.pallaw.swipeandlearnf.domain

import com.pallaw.swipeandlearnf.data.model.RewardDto
import com.pallaw.swipeandlearnf.domain.model.Question
import com.pallaw.swipeandlearnf.domain.model.Reward
import com.pallaw.swipeandlearnf.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun getUserData(): Flow<UserData>
    suspend fun getQuestions(): Flow<List<Question>>
    suspend fun getUserRewards(userId: Int): Flow<List<Reward>>
    suspend fun getNewReward(streakCount: Int) : Flow<RewardDto>
}