package com.pallaw.swipeandlearnf.ui.reward

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.domain.model.Reward

@Composable
fun RewardsListScreen(
    rewardList: List<Reward>,
    onScratchCardRevealed: (reward: Reward) -> Unit,
    modifier: Modifier = Modifier
) {

    var showCouponDialog by remember { mutableStateOf(false) }
    var currentReward: Reward? by remember { mutableStateOf(null) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(rewardList.size) {
                ScratchCard(
                    reward = rewardList[it],
                    onScratchCardClicked = { clickedReward ->
                    showCouponDialog = !showCouponDialog
                    currentReward = clickedReward }
                )
            }
        })

    AnimatedVisibility(visible = showCouponDialog) {
        ScratchCouponDialog(
            reward = currentReward!!,
            onCloseListener = {
                showCouponDialog = !showCouponDialog
            },
            onScratchCouponRevealed = {
                onScratchCardRevealed.invoke(currentReward!!)
            }
        )
    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun ScratchCard(
    reward: Reward,
    onScratchCardClicked: (reward: Reward) -> Unit,
    onScratchCardRevealed: ((reward: Reward) -> Unit)? = null
) {
    Card(
        elevation = 8.dp,
        onClick = {
            onScratchCardClicked.invoke(reward)
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        ScratchCoupon(
            overlayImage = ImageBitmap.imageResource(R.drawable.scratch_card_overlay),
            baseImage = ImageBitmap.imageResource(R.drawable.sample_reward),
            onScratchCouponRevealed = {
                onScratchCardRevealed?.invoke(reward)
            },
            isCouponRevealed = reward.isRevealed
        )
    }
}

@Preview
@Composable
private fun PreviewRewardsScreen() {
    val rewardList = (1..10).map { Reward(id = it, type = Reward.Type.SKIP) }
    RewardsListScreen(
        rewardList = rewardList,
        onScratchCardRevealed = {}
    )
}