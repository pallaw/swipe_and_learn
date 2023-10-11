package com.pallaw.swipeandlearnf.data.model

data class RewardDto(
    val count: Int,
    val details: List<Detail> = emptyList(),
    val type: String
) {
    data class Detail(
        val code: String,
        val count: Int,
        val name: String
    )
}