package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.destination.Picture
import com.goforer.phogal.presentation.ui.navigation.destination.Picture.pictureRouteArgs
import com.goforer.phogal.presentation.ui.navigation.destination.SearchPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.UserPhotos
import com.goforer.phogal.presentation.ui.navigation.destination.UserPhotos.userPhotosRouteArgs
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Stable
fun NavGraphBuilder.galleryGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(
            route = searchPhotosRoute,
            enterTransition = {
                fadeIn(initialAlpha = 1f, animationSpec = tween(700))
            },
            exitTransition = {
                fadeOut(targetAlpha = 1f, animationSpec = tween(700))
            }
        ) {backStackEntry ->
            val navBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route)
            }

            SearchPhotos.screen(navController, backStackEntry.arguments, navBackStackEntry)
        }

        composable(
            route = pictureRouteArgs,
            arguments = Picture.arguments,
            enterTransition = {
                fadeIn(initialAlpha = 1f, animationSpec = tween(700))
            },
            exitTransition = {
                fadeOut(targetAlpha = 1f, animationSpec = tween(700))
            }
        ) {backStackEntry ->
            val navBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route)
            }

            Picture.screen(navController, backStackEntry.arguments, navBackStackEntry)
        }

        composable(
            route = userPhotosRouteArgs,
            arguments = UserPhotos.arguments,
            enterTransition = {
                fadeIn(initialAlpha = 1f, animationSpec = tween(700))
            },
            exitTransition = {
                fadeOut(targetAlpha = 1f, animationSpec = tween(700))
            }
        ) {backStackEntry ->
            val navBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route)
            }

            UserPhotos.screen(navController, backStackEntry.arguments, navBackStackEntry)
        }
    }
}