package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.Notification
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationsStartRoute

@Stable
fun NavGraphBuilder.notificationGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = notificationsStartRoute) { backStackEntry ->
            Notification.screen(navController, backStackEntry, route)
        }
    }
}