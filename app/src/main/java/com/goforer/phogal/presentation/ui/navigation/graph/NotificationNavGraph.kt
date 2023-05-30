package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.Community
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationsStartRoute

@Stable
fun NavGraphBuilder.notificationGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = notificationsStartRoute) { backStackEntry ->
            val navBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route)
            }

            Community.screen(navController, navBackStackEntry)
        }
    }
}