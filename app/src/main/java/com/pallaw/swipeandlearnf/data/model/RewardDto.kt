package com.pallaw.swipeandlearnf.data.model

data class RewardDto(
    val id: Int,
    val count: Int,
    val details: List<Detail> = emptyList(),
    val type: String,
    val isRevealed: Boolean = false
) {
    data class Detail(
        val code: String,
        val count: Int,
        val name: String
    )
}