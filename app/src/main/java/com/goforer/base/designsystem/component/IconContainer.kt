package com.goforer.base.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun IconContainer(size: Dp, content: @Composable () -> Unit) {
    Surface(
        Modifier.size(width = size, height = size),
        CircleShape
    ) {
        content()
    }
}