package com.goforer.phogal.presentation.ui.navigation.destination

import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationsStartRoute

object Notification : PhogalDestination {
    override val icon = Icons.Sharp.Notifications
    override val route = notificationsStartRoute
    override val screen: @Composable (
        navController: NavHostController,
        arguments: Bundle?,
        navBackStackEntry: NavBackStackEntry
    ) -> Unit = { navController, _, _ ->
        navController.currentBackStackEntry?.let {
        }
    }
}