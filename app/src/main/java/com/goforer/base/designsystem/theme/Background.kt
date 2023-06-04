package com.goforer.base.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary

/**
 * A class to model background color and tonal elevation values for Now in Android.
 */
@Immutable
data class BackgroundTheme(
    val color: Color = ColorBgSecondary,
    val tonalElevation: Dp = Dp.Unspecified,
)

/**
 * A composition local for [BackgroundTheme].
 */
val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }