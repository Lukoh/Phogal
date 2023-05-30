package com.goforer.phogal.presentation.ui.navigation.destination

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Commute
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.communitiesStartRoute

object Community : PhogalDestination {
    override val icon = Icons.Sharp.Commute
    override val route = communitiesStartRoute
    override val screen: @Composable (
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { _, _, _ ->
    }
}