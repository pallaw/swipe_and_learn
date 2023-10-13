package com.pallaw.swipeandlearnf.data

import com.pallaw.swipeandlearnf.data.model.RewardDto
import com.pallaw.swipeandlearnf.domain.GameRepository
import com.pallaw.swipeandlearnf.domain.model.Question
import com.pallaw.swipeandlearnf.domain.model.Reward
import com.pallaw.swipeandlearnf.domain.model.Subject
import com.pallaw.swipeandlearnf.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGameRepositoryImp : GameRepository {
    override suspend fun getUserData(): Flow<UserData> {
        return flow {
            emit(
                UserData(
                    streakCount = 0,
                    rewards = (1..5).map {
                        Reward(
                            id = it,
                            count = (1..5).random(),
                            type = Reward.Type.DIAMOND
                        )
                    }
                )
            )
        }
    }

    override suspend fun getQuestions(): Flow<List<Question>> {
        return flow {
            val data = (1..10).map {
                val answer = listOf(true, false).random()
                Question(
                    id = it,
                    answer = answer,
                    question = "Question no $it",
                    hint = "Hint is $answer"
                )
            }.toMutableList()
            data.add(0, Question())
            emit(
                data
            )
        }
    }

    override suspend fun getUserRewards(userId: Int): Flow<List<Reward>> {
        return flow {
            emit(
                (1..10).map {
                    Reward(
                        id = it,
                        count = (1..5).random(),
                        isRevealed = listOf(true, false).random(),
                        type = listOf(
                            Reward.Type.DIAMOND,
                            Reward.Type.HINT,
                            Reward.Type.SKIP,
                            Reward.Type.PW_COINS
                        ).random()
                    )
                }.toList()
            )
        }
    }

    override suspend fun getNewReward(streakCount: Int): Flow<RewardDto> {
        return flow {
            Reward(
                id = (1..1000).random(),
                count = (1..5).random(),
                type = listOf(
                    Reward.Type.DIAMOND,
                    Reward.Type.HINT,
                    Reward.Type.SKIP,
                    Reward.Type.PW_COINS
                ).random(),
                isRevealed = listOf(true, false).random()
            )
        }
    }

    override suspend fun getSubjectsAndChapters(): Flow<List<Subject>> {
        return flow {
            emit(
                (1..10).map { subjectCounter ->
                    Subject(
                        _id = "${subjectCounter}",
                        name = "Subject ${subjectCounter}",
                        chapters = (1..5).map { chapterCounter -> "Subject ${subjectCounter}, Chapter ${chapterCounter}" }
                    )
                }
            )
        }
    }
}