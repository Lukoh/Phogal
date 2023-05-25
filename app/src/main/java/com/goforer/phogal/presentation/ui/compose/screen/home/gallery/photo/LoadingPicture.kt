package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo

import android.content.res.Configuration
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goforer.base.designsystem.component.LoadingIndicator
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray2
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
                    .background(ColorSystemGray2)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
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
                        .background(ColorSystemGray2)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {}
            }

            LoadingIndicator(modifier, DarkGreen10)
        }
    }
}