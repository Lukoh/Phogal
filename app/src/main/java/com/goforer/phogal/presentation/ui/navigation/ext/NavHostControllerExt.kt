package com.goforer.phogal.presentation.ui.navigation.ext

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

fun NavHostController.navigateSingleTopToGraph(route: String, navOptions: NavOptions? = null) =
    this.navigate(route, navOptions)

fun NavHostController.navigateSingleTopToGraph(route: String) =
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateSingleTopTo(route: String) =
    navigate(route) {
        popUpTo(route) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }