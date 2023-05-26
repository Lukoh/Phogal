package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.photosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.Gallery
import com.goforer.phogal.presentation.ui.navigation.destination.Picture
import com.goforer.phogal.presentation.ui.navigation.destination.Picture.routeWithArgs
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Stable
fun NavGraphBuilder.galleryGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route,) {
        composable(
            route = photosStartRoute,
            enterTransition = {
                fadeIn(animationSpec = tween(2000))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(2000))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(2000))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(2000))
            }
        ) {
            Gallery.screen(navController, it.arguments)
        }

        composable(route = routeWithArgs, arguments = Picture.arguments) {
            Picture.screen(navController, it.arguments)
        }
    }
}