package com.goforer.phogal.presentation.ui.navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Stable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.photosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.Photos
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Stable
fun NavGraphBuilder.photoGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(route = photosStartRoute) {
            Photos.screen(navController, it.arguments)
        }
    }
}