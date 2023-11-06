package com.goforer.phogal.presentation.ui.navigation.destination

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Photo
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.goforer.phogal.data.model.local.home.gallery.NameArgument
import com.goforer.phogal.data.model.local.home.gallery.PictureArgument
import com.goforer.phogal.data.model.local.home.gallery.WebViewArgument
import com.goforer.phogal.presentation.ui.compose.screen.home.popularphotos.PopularPhotosScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.popularPhotosStartRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateTo
import com.google.gson.Gson

object PopularPhotos : PhogalDestination {
    override val icon = Icons.Sharp.Photo
    override val route = popularPhotosStartRoute
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, _, _ ->

        PopularPhotosScreen(
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