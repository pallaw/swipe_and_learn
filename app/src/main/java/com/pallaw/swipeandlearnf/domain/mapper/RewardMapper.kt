package com.pallaw.swipeandlearnf.domain.mapper

import com.pallaw.swipeandlearnf.data.model.RewardDto
import com.pallaw.swipeandlearnf.domain.model.Reward

fun RewardDto.toReward(): Reward{
    return Reward(
        count = this.count,
        type = when(this.type) {
            "diamond" -> {Reward.Type.DIAMOND}
            "skip" -> {Reward.Type.SKIP}
            "hint" -> {Reward.Type.HINT}
            "pw_coins" -> {Reward.Type.PW_COINS}
            "pw_discount_cards" -> {Reward.Type.PW_DISCOUNT_CARDS}
            else -> {null}
        },
        details = this.details.map {
            Reward.Detail(
                code = it.code,
                count = it.count,
                name = it.name
            )
        }
    )
}