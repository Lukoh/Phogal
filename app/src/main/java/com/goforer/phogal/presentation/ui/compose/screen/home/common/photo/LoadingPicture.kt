package com.goforer.phogal.presentation.ui.compose.screen.home.common.photo

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goforer.base.designsystem.component.LoadingIndicator
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7
import com.goforer.phogal.presentation.ui.theme.DarkGreen10
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun LoadingPicture(
    modifier: Modifier = Modifier,
    enableLoadIndicator: Boolean = false
) {
    BoxWithConstraints(modifier = modifier.clip(RoundedCornerShape(4.dp))) {
        Column {
            Card(
                Modifier
                    .fillMaxSize()
                    .background(ColorSystemGray7)
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.surface,
                        placeholderFadeTransitionSpec = { tween(durationMillis = 400) },
                        contentFadeTransitionSpec = { tween(durationMillis = 400) },
                        highlight = PlaceholderHighlight.shimmer(
                            animationSpec = infiniteRepeatable(
                                animation = keyframes {
                                    durationMillis = 800
                                    0f at 0
                                    0f at 200
                                    1f at 800 with FastOutSlowInEasing
                                },
                                repeatMode = RepeatMode.Reverse,
                                initialStartOffset = StartOffset(
                                    offsetType = StartOffsetType.FastForward,
                                    offsetMillis = 200
                                )
                            )
                        )
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {}
        }

        if (enableLoadIndicator)
            LoadingIndicator(modifier.padding(vertical = 22.dp), DarkGreen10)
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    showSystemUi = true
)
@Composable
fun LoadingPicturePreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        BoxWithConstraints(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
        ) {
            Column {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                        .background(ColorSystemGray7)
                        .placeholder(
                            visible = true,
                            color = MaterialTheme.colorScheme.surface,
                            placeholderFadeTransitionSpec = { tween(durationMillis = 400) },
                            contentFadeTransitionSpec = { tween(durationMillis = 400) },
                            highlight = PlaceholderHighlight.shimmer(
                                animationSpec = infiniteRepeatable(
                                    animation = keyframes {
                                        durationMillis = 800
                                        0f at 0
                                        0f at 200
                                        1f at 800 with FastOutSlowInEasing
                                    },
                                    repeatMode = RepeatMode.Reverse,
                                    initialStartOffset = StartOffset(
                                        offsetType = StartOffsetType.FastForward,
                                        offsetMillis = 200
                                    )
                                )
                            )
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {}
            }

            LoadingIndicator(modifier, DarkGreen10)
        }
    }
}