package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goforer.phogal.R
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray7

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter =  painterResource(id = R.drawable.img_error),
            modifier = Modifier.sizeIn(250.dp, 250.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            contentDescription = ""
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium.copy(color = ColorSystemGray7),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = message,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium.copy(color = ColorSystemGray7),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onRetry()
        }) {
            Text(
                text = "Try Again",
                style = MaterialTheme.typography.titleMedium.copy(color = ColorSystemGray7),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium
            )
        }
    }
}