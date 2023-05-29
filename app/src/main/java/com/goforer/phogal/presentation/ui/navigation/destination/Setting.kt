package com.goforer.phogal.presentation.ui.navigation.destination

import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.goforer.phogal.presentation.ui.compose.screen.home.setting.SettingScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingStartRoute
import timber.log.Timber

object Setting : PhogalDestination {
    override val icon = Icons.Sharp.Settings
    override val route = settingStartRoute
    override val screen: @Composable (
        navController: NavHostController,
        arguments: Bundle?,
        navBackStackEntry: NavBackStackEntry
    ) -> Unit = { _, _, _ ->
        SettingScreen(
            onItemClicked = { index ->
                Timber.d("${"Index - "}${index}")
            }
        )
    }
}