package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.Picture
import com.goforer.phogal.presentation.ui.navigation.destination.Picture.pictureRouteArgs
import com.goforer.phogal.presentation.ui.navigation.destination.SearchPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.UserPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.UserPhotos.userPhotosRouteArgs

@Stable
fun NavGraphBuilder.galleryGraph(
    navController: NavHostController,
    startDestination: String,
    route: String,
    storage: LocalStorage
) {
    navigation(startDestination = startDestination, route = route) {
        composable(
            route = searchPhotosStartRoute
        ) { backStackEntry ->
            SearchPhotos.screen(navController, backStackEntry, route, storage)
        }

        composable(
            route = pictureRouteArgs,
            arguments = Picture.arguments
        ) { backStackEntry ->
            Picture.screen(navController, backStackEntry, route, storage)
        }

        composable(
            route = userPhotosRouteArgs,
            arguments = UserPhotos.arguments
        ) { backStackEntry ->
            UserPhotos.screen(navController, backStackEntry, route, storage)
        }
    }
}