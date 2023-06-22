package com.goforer.base.designsystem.animation

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun GenericCubicAnimationShape(
    visible: Boolean = false,
    duration: Int = 100,
    content: @Composable (animatedShape: GenericShape, visible: Boolean) -> Unit,
) {
    val animationProgress by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing)
    )
    val transition = updateTransition(targetState = animationProgress, label = "")
    val animatedShape by transition.animateValue(
        TwoWayConverter(
            convertToVector = { AnimationVector1D(0F) },
            convertFromVector = { GenericShape { _, _ -> } }
        ),
        label = ""
    ) { progress ->
        GenericShape { size, _ ->
            val centerH = size.width / 2F
            val multiplierW = 1.5F + size.height / size.width
            moveTo(
                x = centerH - centerH * progress * multiplierW,
                y = size.height,
            )
            val currentWidth = (centerH * progress * multiplierW * 2.5F)
            cubicTo(
                x1 = centerH - centerH * progress * 1.5F,
                y1 = size.height - currentWidth * 0.5F,
                x2 = centerH + centerH * progress * 1.5F,
                y2 = size.height - currentWidth * 0.5F,
                x3 = centerH + centerH * progress * multiplierW,
                y3 = size.height,
            )
            close()
        }
    }

    if (animationProgress != 0F) {
        content(animatedShape, visible)
    }
}