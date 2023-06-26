package com.goforer.phogal.presentation.ui.navigation.destination

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.goforer.phogal.presentation.ui.compose.screen.home.notifcation.notifications.NotificationsScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationsStartRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateTo

object Notification : PhogalDestination {
    override val icon = Icons.Sharp.Notifications
    override val route = notificationsStartRoute
    override val screen: @Composable (
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, _, _ ->
        NotificationsScreen(
            onItemClicked = { id ->
                navController.navigateTo("${notificationRoute}/$id")
            }
        )
    }
}