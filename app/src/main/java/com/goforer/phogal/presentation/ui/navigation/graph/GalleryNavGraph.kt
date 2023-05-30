package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.destination.Picture
import com.goforer.phogal.presentation.ui.navigation.destination.Picture.pictureRouteArgs
import com.goforer.phogal.presentation.ui.navigation.destination.SearchPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.UserPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.UserPhotos.userPhotosRouteArgs

@Stable
fun NavGraphBuilder.galleryGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(
            route = searchPhotosRoute
        ) { backStackEntry ->
            SearchPhotos.screen(navController, backStackEntry)
        }

        composable(
            route = pictureRouteArgs,
            arguments = Picture.arguments
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(pictureRouteArgs)
            }

            Picture.screen(navController, backStackEntry)
        }

        composable(
            route = userPhotosRouteArgs,
            arguments = UserPhotos.arguments
        ) { backStackEntry ->
            UserPhotos.screen(navController, backStackEntry)
        }
    }
}