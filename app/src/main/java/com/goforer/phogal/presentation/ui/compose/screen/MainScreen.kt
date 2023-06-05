package com.goforer.phogal.presentation.ui.compose.screen

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goforer.base.designsystem.component.Background
import com.goforer.base.designsystem.component.GradientBackground
import com.goforer.base.designsystem.theme.GradientColors
import com.goforer.base.designsystem.theme.LocalGradientColors
import com.goforer.base.storage.LocalStorage
import com.goforer.base.utils.connect.NetworkMonitor
import com.goforer.phogal.presentation.stateholder.uistate.MainScreenState
import com.goforer.phogal.presentation.stateholder.uistate.rememberMainScreenState
import com.goforer.phogal.presentation.ui.compose.screen.home.BottomNavDestination.Gallery
import com.goforer.phogal.presentation.ui.compose.screen.home.HomeScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.OfflineScreen

@Composable
fun MainScreen(
    networkMonitor: NetworkMonitor,
    windowSizeClass: WindowSizeClass,
    state: MainScreenState = rememberMainScreenState(
        windowSizeClass = windowSizeClass,
        networkMonitor = networkMonitor
    ),
    storage: LocalStorage
) {
    /*
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

        val currentScreen = Screens.find { it.route == state.currentDestination?.route } ?: Photos

        HomeScreen(modifier = Modifier, state = state)
    }

     */

    val shouldShowGradientBackground = state.currentTopLevelDestination == Gallery

    Background {
        GradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {
            val isOffline by state.isOffline.collectAsStateWithLifecycle()

            if (isOffline)
                OfflineScreen(modifier = Modifier)
            else
                HomeScreen(modifier = Modifier, state = state, storage)
        }
    }
}