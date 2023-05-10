package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray2
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun LoadingPhotos(
    modifier: Modifier = Modifier,
    count: Int
) {
    BoxWithConstraints(modifier = modifier.clip(RoundedCornerShape(4.dp))) {
        Column {
            for(i in 1..count) {
                Card(
                    Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .background(ColorSystemGray2)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {}
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
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
fun LoadingPhotosPreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        BoxWithConstraints(
            modifier = modifier
                .clip(RoundedCornerShape(4.dp))
        ) {
            Column {
                for(i in 1..3) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .height(256.dp)
                            .background(ColorSystemGray2)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                            ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {}
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}