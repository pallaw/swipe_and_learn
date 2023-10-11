package com.pallaw.swipeandlearnf.domain.model

data class Reward(
    val count: Int = 0,
    val details: List<Detail> = emptyList(),
    val type: Type?,
    val isRevealed: Boolean = false
) {

    enum class Type{
        DIAMOND,
        HINT,
        SKIP,
        PW_COINS,
        PW_DISCOUNT_CARDS
    }

    data class Detail(
        val code: String,
        val count: Int,
        val name: String
    )
}