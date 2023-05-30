package com.goforer.phogal.presentation.ui.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

interface PhogalDestination {
    val icon: ImageVector
    val route: String
    val screen: @Composable (NavHostController, NavBackStackEntry) -> Unit

    companion object {
        // Photo Bottom Navigation
        internal const val photosHomeRoute = "photoHome"
        internal const val searchPhotosRoute = "photoHome/searchPhotos"
        internal const val searchPictureRoute = "photoHome/searchPicture"
        internal const val userPhotosRoute = "photoHome/userPhotos"

        // Community Bottom Navigation
        internal const val communityHomeRoute = "communityHome"
        internal const val communitiesStartRoute = "communityHome/communities"

        // Notification Bottom Navigation
        internal const val notificationHomeRoute = "notificationHome"
        internal const val notificationsStartRoute = "notificationHome/notifications"

        // Setting Bottom Navigation
        internal const val settingHomeRoute = "settingHome"
        internal const val settingStartRoute = "settingHome/setting"
    }
}