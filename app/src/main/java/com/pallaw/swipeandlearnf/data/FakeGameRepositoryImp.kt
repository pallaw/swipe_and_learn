package com.pallaw.swipeandlearnf.data

import com.pallaw.swipeandlearnf.domain.GameRepository
import com.pallaw.swipeandlearnf.data.model.QuestionDto
import com.pallaw.swipeandlearnf.data.model.RewardDto
import com.pallaw.swipeandlearnf.domain.model.Reward
import com.pallaw.swipeandlearnf.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FakeGameRepositoryImp(): GameRepository {
    override fun getUserData(): Flow<UserData> {
        return flow {
            emit(
                UserData(
                    streakCount = 5,
                    rewards = (1..5).map {
                        Reward(
                            count = Random(5).nextInt(),
                            type = Reward.Type.DIAMOND
                        )
                    }
                )
            )
        }
    }

    override fun getQuestions(): Flow<List<QuestionDto>> {
        TODO()
    }

    override fun getRewards(): Flow<List<RewardDto>> {
        TODO()
    }

    override fun getReward(streakCount: Int): Flow<RewardDto> {
        TODO()
    }
}