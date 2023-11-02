package com.goforer.phogal.presentation.ui.navigation.destination

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.sharp.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.goforer.phogal.data.model.local.home.gallery.NameArgument
import com.goforer.phogal.data.model.local.home.gallery.PictureArgument
import com.goforer.phogal.data.model.local.home.gallery.WebViewArgument
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.rememberPhotoContentState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.webview.WebViewScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.common.photo.PictureScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.SearchPhotosScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.common.user.userphotos.UserPhotosScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.pictureRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.userPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.webViewRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateTo
import com.google.gson.Gson

object SearchPhotos : PhogalDestination {
    override val icon = Icons.Sharp.ViewList
    override val route = searchPhotosStartRoute
    @OptIn(ExperimentalComposeUiApi::class)
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, _, _ ->
        SearchPhotosScreen(
            onItemClicked = { id ->
                val pictureArgument = PictureArgument(
                    id = id,
                    visibleViewPhotosButton = true
                )
                val gson = Gson()
                val json = Uri.encode(gson.toJson(pictureArgument))

                navController.navigateTo("${Picture.route}/$json")
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

                navController.navigateTo(route = "${UserPhotos.route}/$json")
            },
            onOpenWebView = { firstName, url ->
                val webViewArgument = WebViewArgument(
                    firstName = firstName,
                    url = url
                )
                val gson = Gson()
                val json = Uri.encode(gson.toJson(webViewArgument))

                navController.navigateTo(route = "${WbeView.route}/$json")
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

        Gson().fromJson(argument, PictureArgument::class.java)?.let {
            PictureScreen(
                state = rememberPhotoContentState(
                    idState = rememberSaveable { mutableStateOf(it.id) },
                    visibleViewButtonState = rememberSaveable { mutableStateOf(it.visibleViewPhotosButton) }
                ),
                onViewPhotos = { name, firstName, lastName, username ->
                    val nameArgument = NameArgument(
                        name = name,
                        firstName = firstName,
                        lastName = lastName,
                        username = username
                    )
                    val gson = Gson()
                    val json = Uri.encode(gson.toJson(nameArgument))

                    navController.navigateTo(route = "${UserPhotos.route}/$json")
                },
                onBackPressed = {
                    navController.navigateUp()
                },
                onOpenWebView = { firstName, url ->
                    val webViewArgument = WebViewArgument(
                        firstName = firstName,
                        url = url
                    )
                    val gson = Gson()
                    val json = Uri.encode(gson.toJson(webViewArgument))

                    navController.navigateTo(route = "${WbeView.route}/$json")
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

    @OptIn(ExperimentalComposeUiApi::class)
    @Stable
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, backStackEntry, _ ->
        val argument = backStackEntry.arguments?.getString(argumentTypeArg)

        Gson().fromJson(argument, NameArgument::class.java)?.let {
            UserPhotosScreen(
                nameArgument = it,
                onItemClicked = { id ->
                    val pictureArgument = PictureArgument(
                        id = id,
                        visibleViewPhotosButton = false
                    )
                    val gson = Gson()
                    val json = Uri.encode(gson.toJson(pictureArgument))

                    navController.navigateTo(route = "${Picture.route}/$json")
                },
                onBackPressed = {
                    navController.navigateUp()
                },
            )
        }
    }
}

object WbeView : PhogalDestination {
    override val icon = Icons.Filled.OpenInBrowser
    override val route = webViewRoute
    private const val argumentTypeArg = "argument"
    val webViewRouteArgs = "$route/{$argumentTypeArg}"

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

        Gson().fromJson(argument,WebViewArgument::class.java)?.let {
            WebViewScreen(
                firstName = it.firstName,
                url = it.url,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}