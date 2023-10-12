package com.pallaw.swipeandlearnf.ui.reward

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pallaw.swipeandlearnf.domain.model.Reward

@Composable
fun RewardsListScreen(
    state: RewardScreenContract.State = RewardScreenContract.State(),
    rewardClicked: (reward: Reward) -> Unit,
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
            items(state.rewards.size) {
                ScratchCard(state.rewards[it]) { clickedReward ->
                    showCouponDialog = !showCouponDialog
                    currentReward = clickedReward
                }
            }
        })

    AnimatedVisibility(visible = showCouponDialog) {
        ScratchCouponDialog(reward = currentReward!!) {
            showCouponDialog = !showCouponDialog
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScratchCard(reward: Reward, cardClicked: (reward: Reward) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp),
        backgroundColor = Color.Red,
        elevation = 8.dp,
        onClick = {
            cardClicked.invoke(reward)
        }
    ) {
        Box {

            Text(
                text = "$reward",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRewardsScreen() {
    val rewardList = (1..10).map { Reward(id = it, type = Reward.Type.SKIP) }
    RewardsListScreen(
        state = RewardScreenContract.State().copy(rewards = rewardList),
        rewardClicked = {}
    )
}