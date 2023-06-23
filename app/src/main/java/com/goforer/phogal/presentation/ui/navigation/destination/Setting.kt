package com.goforer.phogal.presentation.ui.navigation.destination

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FollowTheSigns
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.goforer.phogal.data.model.local.home.gallery.NameArgument
import com.goforer.phogal.data.model.local.home.gallery.PictureArgument
import com.goforer.phogal.data.model.local.home.gallery.WebViewArgument
import com.goforer.phogal.presentation.ui.compose.screen.home.setting.SettingScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark.BookmarkedPhotosScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.setting.following.FollowingUsersScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.setting.notification.NotificationSettingScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingBookmarkedPhotosRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.pictureRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.webViewRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateTo
import com.google.gson.Gson

object Setting : PhogalDestination {
    override val icon = Icons.Sharp.Settings
    override val route = settingStartRoute
    override val screen: @Composable (
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, _, _ ->
        SettingScreen(
            onItemClicked = { index ->
                when(index) {
                    0 ->  navController.navigateTo(BookmarkedPhotos.route)
                    1 -> navController.navigateTo(FollowingUsers.route)
                    2 -> navController.navigateTo(NotificationSetting.route)
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
        route: String
    ) -> Unit = { navController, _, _ ->
        BookmarkedPhotosScreen(
            onItemClicked = { picture, _ ->
                val pictureArgument = PictureArgument(
                    id = picture.id,
                    visibleViewPhotosButton = false
                )
                val gson = Gson()
                val json = Uri.encode(gson.toJson(pictureArgument))

                navController.navigate("${pictureRoute}/$json")
            },
            onBackPressed = {
                navController.navigateUp()
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

                navController.navigate("${webViewRoute}/$json")
            }
        )
    }
}

object FollowingUsers : PhogalDestination {
    override val icon = Icons.Filled.FollowTheSigns
    override val route = PhogalDestination.settingFollowingUsersRoute

    @Stable
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, _, _ ->
        FollowingUsersScreen(
            onBackPressed = {
                navController.navigateUp()
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

object NotificationSetting : PhogalDestination {
    override val icon = Icons.Filled.Notifications
    override val route = PhogalDestination.settingNotificationRoute

    @Stable
    override val screen: @Composable (
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        route: String
    ) -> Unit = { navController, _, _ ->
        NotificationSettingScreen(
            onBackPressed = {
                navController.navigateUp()
            }
        )
    }
}