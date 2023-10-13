package com.pallaw.swipeandlearnf.ui.reward

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.domain.model.Reward

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScratchCouponDialog(
    modifier: Modifier = Modifier,
    reward: Reward,
    onCloseListener: () -> Unit,
    onScratchCouponRevealed: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable {}
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)),
    ) {
        IconButton(
            onClick = { onCloseListener.invoke() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(40.dp),
            )
        }

        ScratchCoupon(
            overlayImage = ImageBitmap.imageResource(R.drawable.scratch_card_overlay),
            baseImage = ImageBitmap.imageResource(R.drawable.sample_reward),
            allowScratch = true,
            currentPathThickness = 80f,
            onScratchCouponRevealed = { onScratchCouponRevealed.invoke() },
            modifier = Modifier
                .align(Alignment.Center)
                .width(300.dp)
                .height(300.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewScratchCouponDialog() {
    MaterialTheme {
        ScratchCouponDialog(
            reward = Reward(
                id = 5,
                type = Reward.Type.DIAMOND
            ),
            onCloseListener = {},
            onScratchCouponRevealed = {}
        )
    }
}