package com.goforer.phogal.presentation.ui.navigation.destination

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.sharp.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.goforer.phogal.data.model.BaseModel
import com.goforer.phogal.data.model.local.home.gallery.Name
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo.PictureScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.searchphotos.SearchPhotosScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.userphotos.UserPhotosScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.pictureRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.userPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateSingleTopTo
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

object SearchPhotos : PhogalDestination {
    override val icon = Icons.Sharp.ViewList
    override val route = searchPhotosRoute
    override val screen: @Composable (
        navController: NavHostController,
        arguments: Bundle?,
        navBackStackEntry: NavBackStackEntry
    ) -> Unit = { navController, _, navBackStackEntry ->
        SearchPhotosScreen(
            navBackStackEntry = navBackStackEntry,
            onItemClicked = { id ->
                val pictureRoute = PictureRoute(
                    id = id,
                    visibleViewPhotosButton = true
                )
                val gson = Gson()
                val json = gson.toJson(pictureRoute)
                navController.navigateSingleTopTo("${Picture.route}/$json")
            },
            onViewPhotos = { name, firstName, lastName, username ->
                val userName = Name(
                    name = name,
                    firstName = firstName,
                    lastName = lastName,
                    username = username
                )
                val gson = Gson()
                val json = gson.toJson(userName)

                navController.navigateSingleTopTo("${UserPhotos.route}/$json")
            }
        )
    }
}

object Picture : PhogalDestination {
    override val icon = Icons.Filled.Photo
    override val route = pictureRoute
    private const val idTypeArg = "id"
    val pictureRouteArgs = "$route/{$idTypeArg}"
    val arguments = listOf(
        navArgument(idTypeArg) { type = NavType.StringType }
    )

    @Stable
    override val screen: @Composable (
        navController: NavHostController,
        arguments: Bundle?,
        navBackStackEntry: NavBackStackEntry
    ) -> Unit = { navController, arguments, navBackStackEntry ->
        val id = arguments?.getString(idTypeArg)
        val pictureRoute = Gson().fromJson(id, PictureRoute::class.java)

        pictureRoute?.let {
            PictureScreen(
                navBackStackEntry = navBackStackEntry,
                id = pictureRoute.id,
                visibleViewPhotosButton = pictureRoute.visibleViewPhotosButton,
                onViewPhotos = { name, firstName, lastName, username ->
                    val userName = Name(
                        name = name,
                        firstName = firstName,
                        lastName = lastName,
                        username = username
                    )
                    val gson = Gson()
                    val json = gson.toJson(userName)
                    navController.navigateSingleTopTo("${UserPhotos.route}/$json")
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
    private const val nameTypeArg = "name"
    val userPhotosRouteArgs = "$route/{$nameTypeArg}"

    val arguments = listOf(
        navArgument(nameTypeArg) { type = NavType.StringType }
    )

    @Stable
    override val screen: @Composable (
        navController: NavHostController,
        arguments: Bundle?,
        navBackStackEntry: NavBackStackEntry
    ) -> Unit = { navController,arguments, navBackStackEntry ->
        val name = arguments?.getString(nameTypeArg)
        val userName = Gson().fromJson(name, Name::class.java)

        name?.let {
            UserPhotosScreen(
                navBackStackEntry = navBackStackEntry,
                name = userName.name,
                firstName = userName.firstName,
                onItemClicked = { id ->
                    val pictureRoute = PictureRoute(
                        id = id,
                        visibleViewPhotosButton = false
                    )
                    val gson = Gson()
                    val json = gson.toJson(pictureRoute)
                    navController.navigateSingleTopTo("${Picture.route}/$json")
                },
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@Parcelize
data class PictureRoute(
    val id: String,
    val visibleViewPhotosButton: Boolean
) : BaseModel(), Parcelable