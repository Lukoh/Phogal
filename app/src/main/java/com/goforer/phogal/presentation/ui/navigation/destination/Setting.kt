package com.goforer.phogal.presentation.ui.navigation.destination

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.model.local.home.gallery.PictureArgument
import com.goforer.phogal.presentation.ui.compose.screen.home.setting.SettingScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark.BookmarkedPhotosScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingBookmarkedPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.pictureRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateSingleTopTo
import com.google.gson.Gson

object Setting : PhogalDestination {
    override val icon = Icons.Sharp.Settings
    override val route = settingStartRoute
    override val screen: @Composable (
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        route: String,
        storage: LocalStorage
    ) -> Unit = { navController, _, _, _ ->
        SettingScreen(
            onItemClicked = { index ->
                when(index) {
                    0 ->  navController.navigateSingleTopTo(BookmarkedPhotos.route)
                    else -> {}
                }
            }
        )
    }
}

object BookmarkedPhotos : PhogalDestination {
    override val icon = Icons.Filled.Photo
    override val route = settingBookmarkedPhotosRoute

    @Stable
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String,
        storage: LocalStorage
    ) -> Unit = { navController, _, _, storage ->
        BookmarkedPhotosScreen(
            storage = storage,
            onItemClicked = { picture, _ ->
                val pictureArgument = PictureArgument(
                    id = picture.id,
                    visibleViewPhotosButton = false
                )
                val gson = Gson()
                val json = Uri.encode(gson.toJson(pictureArgument))

                navController.navigateSingleTopTo("${pictureRoute}/$json")
            },
            onBackPressed = {
                navController.navigateUp()
            }
        )
    }
}