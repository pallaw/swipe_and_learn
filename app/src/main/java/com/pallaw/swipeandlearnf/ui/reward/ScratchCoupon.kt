package com.pallaw.swipeandlearnf.ui.reward

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.IntSize

@ExperimentalComposeUiApi
@Composable
fun ScratchCoupon(
    overlayImage: ImageBitmap,
    baseImage: ImageBitmap,
    modifier: Modifier = Modifier,
    currentPathThickness: Float = 50f,
    allowScratch: Boolean = false,
    isCouponRevealed: Boolean = false,
    onScratchCouponRevealed: (() -> Unit)? = null
) {
    val currentPathState = remember { mutableStateOf(DraggedPath(path = Path())) }
    val movedOffsetState = remember { mutableStateOf<Offset?>(null) }
    val scratchedAreaState = remember { mutableStateOf(0f) }  // To track total scratched area
    val showBaseImageOnly = remember { mutableStateOf(isCouponRevealed) }  // To determine if overlay should be hidden
    val canvasWidth = remember { mutableStateOf(0) }
    val canvasHeight = remember { mutableStateOf(0) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .pointerInteropFilter {
                if (allowScratch) {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            println("CurrentPath/ACTION_DOWN: (${it.x}, ${it.y})")
                            currentPathState.value.path.moveTo(it.x, it.y)
                        }

                        MotionEvent.ACTION_MOVE -> {
                            println("MovedOffset/ACTION_MOVE: (${it.x}, ${it.y})")
                            movedOffsetState.value = Offset(it.x, it.y)

                            // Estimate scratched area and accumulate
                            val scratchedArea = Math.PI * (currentPathThickness / 2) * (currentPathThickness / 2)
                            scratchedAreaState.value += scratchedArea.toFloat()

                            // Check if scratched threshold is reached
                            val canvasArea = canvasWidth.value * canvasHeight.value
                            if (scratchedAreaState.value > 0.9 * canvasArea && !showBaseImageOnly.value) {
                                onScratchCouponRevealed?.invoke()
                                showBaseImageOnly.value = true
                            }
                        }
                    }
                    true
                } else {
                    false
                }
            }
    ) {
        canvasWidth.value = size.width.toInt()
        canvasHeight.value = size.height.toInt()
        val imageSize = IntSize(width = canvasWidth.value, height = canvasHeight.value)

        if (!showBaseImageOnly.value) {
            // Overlay Image to be scratched
            drawImage(
                image = overlayImage,
                dstSize = imageSize
            )

            movedOffsetState.value?.let {
                currentPathState.value.path.addOval(oval = Rect(it, currentPathThickness))
            }

            clipPath(path = currentPathState.value.path, clipOp = ClipOp.Intersect) {
                // Base Image after scratching
                drawImage(
                    image = baseImage,
                    dstSize = imageSize
                )
            }
        } else {
            // Only draw base image
            drawImage(
                image = baseImage,
                dstSize = imageSize
            )
        }
    }
}

data class DraggedPath(
    val path: Path,
    val width: Float = 50f
)