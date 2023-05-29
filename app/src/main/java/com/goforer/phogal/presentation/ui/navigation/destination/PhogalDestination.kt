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
        internal const val photosHomeRoute = "home/photoHome"
        internal const val searchPhotosRoute = "home/photoHome/searchPhotos"
        internal const val pictureRoute = "home/photoHome/picture"
        internal const val userPhotosRoute = "home/photoHome/userPhotos"

        // Community Bottom Navigation
        internal const val communityHomeRoute = "home/communityHome"
        internal const val communitiesStartRoute = "home/communityHome/communities"

        // Notification Bottom Navigation
        internal const val notificationHomeRoute = "home/notificationHome"
        internal const val notificationsStartRoute = "home/notificationHome/notifications"

        // Setting Bottom Navigation
        internal const val settingHomeRoute = "home/settingHome"
        internal const val settingStartRoute = "home/settingHome/setting"
    }
}