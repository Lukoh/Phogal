package com.goforer.base.designsystem.animation

import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun animateIconScale(
    inputScale: Float,
    position: Int,
    delay: Long
): Float {
    var scale by remember { mutableFloatStateOf(inputScale) }
    val animateIconScaleState = animateFloatAsState(
        targetValue = scale,
        animationSpec = FloatSpringSpec(
            dampingRatio = 0.3f
        )
    )

    LaunchedEffect(Unit) {
        delay(delay + position.toLong() * 20)
        scale = 1f
    }

    return animateIconScaleState.value
}