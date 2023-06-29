package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.PopularPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.popularPhotosStartRoute

@Stable
fun NavGraphBuilder.popularPhotosGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = popularPhotosStartRoute) { backStackEntry ->

            PopularPhotos.screen(navController, backStackEntry, route)
        }
    }
}