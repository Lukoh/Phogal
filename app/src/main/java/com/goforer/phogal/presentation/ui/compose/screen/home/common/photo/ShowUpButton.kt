package com.goforer.phogal.presentation.ui.compose.screen.home.common.photo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goforer.phogal.presentation.ui.theme.Blue70

@Composable
fun ShowUpButton(modifier: Modifier, visible: Boolean, onClick: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier
    ) {
        FloatingActionButton(
            modifier = modifier
                .padding(bottom = 8.dp, end = 8.dp),
            backgroundColor = Blue70,
            onClick = onClick
        ) {
            Text("Up!")
        }
    }
}