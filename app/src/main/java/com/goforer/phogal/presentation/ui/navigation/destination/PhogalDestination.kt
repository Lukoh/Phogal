package com.goforer.phogal.presentation.ui.navigation.destination

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

interface PhogalDestination {
    val icon: ImageVector
    val route: String
    val screen: @Composable (navController: NavHostController, arguments: Bundle) -> Unit

    companion object {
        // Photo Bottom Navigation
        internal const val photosHomeRoute = "home/photoHome"
        internal const val photosStartRoute = "home/photoHome/photos"
        internal const val pictureRoute = "home/photoHome/picture"

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