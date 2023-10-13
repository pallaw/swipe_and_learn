package com.pallaw.swipeandlearnf.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pallaw.swipeandlearnf.data.model.QuestionDto
import com.pallaw.swipeandlearnf.data.model.QuestionsResponse
import com.pallaw.swipeandlearnf.data.model.RewardDto
import com.pallaw.swipeandlearnf.domain.GameRepository
import com.pallaw.swipeandlearnf.domain.mapper.toQuestion
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
                    hintCount = 10,
                    skipCount = 5,
                    diamondCount = 50,
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
        val questionResponse: QuestionsResponse = Gson().fromJson(questionResponse, QuestionsResponse::class.java)
        return flow {
            emit(
                questionResponse.data.map { it.toQuestion() }
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