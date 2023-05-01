package com.goforer.phogal.presentation.ui.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.goforer.phogal.presentation.ui.navigation.destination.Photos
import com.goforer.phogal.presentation.ui.compose.screen.home.HomeScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.Screens
import com.goforer.phogal.presentation.ui.compose.screen.landing.LandingScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(windowSizeClass: WindowSizeClass) {
    Surface(color = MaterialTheme.colorScheme.primary) {
        var showLandingScreen by remember { mutableStateOf(true) }

        if (showLandingScreen) {
            LandingScreen(onTimeout = { showLandingScreen = false })
        } else {
            val navController = rememberAnimatedNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination
            val currentScreen = Screens.find { it.route == currentDestination?.route } ?: Photos

            HomeScreen(
                windowSizeClass = windowSizeClass,
                modifier = Modifier,
                navController = navController
            )
        }
    }
}