package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.Picture
import com.goforer.phogal.presentation.ui.navigation.destination.Picture.pictureRouteArgs
import com.goforer.phogal.presentation.ui.navigation.destination.SearchPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.UserPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.UserPhotos.userPhotosRouteArgs
import com.goforer.phogal.presentation.ui.navigation.destination.WbeView
import com.goforer.phogal.presentation.ui.navigation.destination.WbeView.webViewRouteArgs

@Stable
fun NavGraphBuilder.galleryGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(
            route = searchPhotosStartRoute
        ) { backStackEntry ->
            SearchPhotos.screen(navController, backStackEntry, route)
        }

        composable(
            route = pictureRouteArgs,
            arguments = Picture.arguments
        ) { backStackEntry ->
            Picture.screen(navController, backStackEntry, route)
        }

        composable(
            route = userPhotosRouteArgs,
            arguments = UserPhotos.arguments
        ) { backStackEntry ->
            UserPhotos.screen(navController, backStackEntry, route)
        }

        composable(
            route = webViewRouteArgs,
            arguments = WbeView.arguments
        ) { backStackEntry ->
            WbeView.screen(navController, backStackEntry, route)
        }
    }
}