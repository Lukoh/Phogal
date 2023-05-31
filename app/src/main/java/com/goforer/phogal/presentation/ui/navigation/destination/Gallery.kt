package com.goforer.phogal.presentation.ui.navigation.destination

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.sharp.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.goforer.phogal.data.model.local.home.gallery.NameArgument
import com.goforer.phogal.data.model.local.home.gallery.PictureArgument
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photo.PictureViewModel
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photos.GalleryViewModel
import com.goforer.phogal.presentation.stateholder.business.home.gallery.user.UserPhotosViewModel
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo.PictureScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.searchphotos.SearchPhotosScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.userphotos.UserPhotosScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.pictureRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.userPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateSingleTopTo
import com.google.gson.Gson

object SearchPhotos : PhogalDestination {
    override val icon = Icons.Sharp.ViewList
    override val route = searchPhotosRoute
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, backStackEntry, _ ->
        val galleryViewModel = hiltViewModel<GalleryViewModel>(backStackEntry)

        SearchPhotosScreen(
            galleryViewModel = galleryViewModel,
            onItemClicked = { id ->
                val pictureRoute = PictureArgument(
                    id = id,
                    visibleViewPhotosButton = true
                )
                val gson = Gson()
                val json = Uri.encode(gson.toJson(pictureRoute))

                navController.navigateSingleTopTo("${Picture.route}/$json")
            },
            onViewPhotos = { name, firstName, lastName, username ->
                val nameArgument = NameArgument(
                    name = name,
                    firstName = firstName,
                    lastName = lastName,
                    username = username
                )
                val gson = Gson()
                val json = Uri.encode(gson.toJson(nameArgument))

                navController.navigateSingleTopTo(route = "${UserPhotos.route}/$json")
            }
        )
    }
}

object Picture : PhogalDestination {
    override val icon = Icons.Filled.Photo
    override val route = pictureRoute
    private const val argumentTypeArg = "argument"
    val pictureRouteArgs = "$route/{$argumentTypeArg}"
    val arguments = listOf(
        navArgument(argumentTypeArg) { type = NavType.StringType }
    )

    @Stable
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, backStackEntry, _ ->
        val argument = backStackEntry.arguments?.getString(argumentTypeArg)
        val pictureArgument = Gson().fromJson(argument, PictureArgument::class.java)
        val pictureViewModel = hiltViewModel<PictureViewModel>(backStackEntry)

        pictureArgument?.let {
            PictureScreen(
                pictureViewModel = pictureViewModel,
                id = pictureArgument.id,
                visibleViewPhotosButton = pictureArgument.visibleViewPhotosButton,
                onViewPhotos = { name, firstName, lastName, username ->
                    val nameArgument = NameArgument(
                        name = name,
                        firstName = firstName,
                        lastName = lastName,
                        username = username
                    )
                    val gson = Gson()
                    val json = Uri.encode(gson.toJson(nameArgument))

                    navController.navigateSingleTopTo(route = "${UserPhotos.route}/$json")
                },
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}

object UserPhotos : PhogalDestination {
    override val icon = Icons.Filled.ViewList
    override val route = userPhotosRoute
    private const val argumentTypeArg = "argument"
    val userPhotosRouteArgs = "$route/{$argumentTypeArg}"

    val arguments = listOf(
        navArgument(argumentTypeArg) { type = NavType.StringType }
    )

    @Stable
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, backStackEntry, _ ->
        val argument = backStackEntry.arguments?.getString(argumentTypeArg)
        val nameArgument = Gson().fromJson(argument, NameArgument::class.java)
        val userPhotosViewModel = hiltViewModel<UserPhotosViewModel>(backStackEntry)

        nameArgument?.let {
            UserPhotosScreen(
                name = nameArgument.name,
                firstName = nameArgument.firstName,
                userPhotosViewModel = userPhotosViewModel,
                onItemClicked = { id ->
                    val pictureRoute = PictureArgument(
                        id = id,
                        visibleViewPhotosButton = false
                    )
                    val gson = Gson()
                    val json = Uri.encode(gson.toJson(pictureRoute))

                    navController.navigateSingleTopTo(route = "${Picture.route}/$json")
                },
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}