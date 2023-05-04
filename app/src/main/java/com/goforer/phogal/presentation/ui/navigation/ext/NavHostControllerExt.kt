package com.goforer.phogal.presentation.ui.navigation.ext

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

fun NavController.navigateSingleTopToGraph(route: String, navOptions: NavOptions? = null) =
    this.navigate(route, navOptions)

fun NavHostController.navigateSingleTopTo(route: String) =
    navigate(route) {
        popUpTo(route) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }