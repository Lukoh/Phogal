package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.BookmarkedPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingBookmarkedPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.Picture
import com.goforer.phogal.presentation.ui.navigation.destination.Picture.pictureRouteArgs
import com.goforer.phogal.presentation.ui.navigation.destination.Setting

@Stable
fun NavGraphBuilder.settingGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = settingStartRoute) { backStackEntry ->
            val navBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route)
            }

            Setting.screen(navController, navBackStackEntry, route)
        }

        composable(
            route = settingBookmarkedPhotosRoute
        ) { backStackEntry ->
            BookmarkedPhotos.screen(navController, backStackEntry, route)
        }

        composable(
            route = pictureRouteArgs,
            arguments = Picture.arguments
        ) { backStackEntry ->
            Picture.screen(navController, backStackEntry, route)
        }
    }
}