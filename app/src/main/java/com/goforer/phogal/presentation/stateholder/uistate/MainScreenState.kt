package com.goforer.phogal.presentation.stateholder.uistate

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.goforer.base.utils.connect.NetworkMonitor
import com.goforer.phogal.presentation.ui.compose.screen.home.BottomNavDestination
import com.goforer.phogal.presentation.ui.compose.screen.home.BottomNavDestination.Gallery
import com.goforer.phogal.presentation.ui.compose.screen.home.BottomNavDestination.Community
import com.goforer.phogal.presentation.ui.compose.screen.home.BottomNavDestination.Notification
import com.goforer.phogal.presentation.ui.compose.screen.home.BottomNavDestination.Setting
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.communitiesStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationsStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingStartRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateSingleTopToGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
class MainScreenState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: BottomNavDestination?
        @Composable get() = when (currentDestination?.route) {
            searchPhotosStartRoute -> Gallery
            communitiesStartRoute -> Community
            notificationsStartRoute -> Notification
            settingStartRoute -> Setting
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    fun navigateToTopLevelDestination(topLevelDestination: BottomNavDestination) {
        trace("Navigation: ${topLevelDestination.route}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            navController.navigateSingleTopToGraph(topLevelDestination.route, topLevelNavOptions)
        }
    }
}

@Composable
fun rememberMainScreenState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): MainScreenState {
    return remember(navController, coroutineScope, windowSizeClass, networkMonitor) {
        MainScreenState(
            navController = navController,
            coroutineScope = coroutineScope,
            windowSizeClass = windowSizeClass,
            networkMonitor = networkMonitor
        )
    }
}