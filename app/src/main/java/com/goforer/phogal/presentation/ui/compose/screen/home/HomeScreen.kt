package com.goforer.phogal.presentation.ui.compose.screen.home

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.goforer.phogal.R
import com.goforer.phogal.presentation.ui.navigation.destination.Community
import com.goforer.phogal.presentation.ui.navigation.destination.Notification
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.communitiesStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.communityHomeRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationHomeRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationsStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.photosHomeRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.photosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingHomeRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.Photos
import com.goforer.phogal.presentation.ui.navigation.destination.Setting
import com.goforer.phogal.presentation.ui.navigation.ext.navigateSingleTopToGraph
import com.goforer.phogal.presentation.ui.navigation.graph.communityGraph
import com.goforer.phogal.presentation.ui.navigation.graph.notificationGraph
import com.goforer.phogal.presentation.ui.navigation.graph.photoGraph
import com.goforer.phogal.presentation.ui.navigation.graph.settingGraph
import com.goforer.phogal.presentation.ui.theme.ColorBottomBar
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

val Screens = listOf(
    Photos,
    Community,
    Notification,
    Setting
)


@Stable
sealed class BottomNavItem(val route: String, @DrawableRes val icon: Int, @StringRes val title: Int) {
    object Photo : BottomNavItem(photosHomeRoute, R.drawable.ic_photo, R.string.bottom_navigation_photo)
    object Community :  BottomNavItem(communityHomeRoute, R.drawable.ic_community, R.string.bottom_navigation_community)
    object Notification :  BottomNavItem(notificationHomeRoute, R.drawable.ic_notification, R.string.bottom_navigation_notification)
    object Setting : BottomNavItem(settingHomeRoute, R.drawable.ic_setting, R.string.bottom_navigation_setting)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController()
) {
    var bottomBarVisible by remember { mutableStateOf(false) }
    val bottomBarOffset by animateDpAsState(targetValue = if (bottomBarVisible) 0.dp else 56.dp)
    val showBottomBar = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                val items = listOf(
                    BottomNavItem.Photo,
                    BottomNavItem.Community,
                    BottomNavItem.Notification,
                    BottomNavItem.Setting,
                )

                BottomNavigation(
                    backgroundColor = ColorBottomBar,
                    contentColor = Color(0xFF3F414E),
                    elevation = 5.dp,
                    modifier = if (bottomBarVisible)
                        modifier.navigationBarsPadding()
                    else
                        modifier.offset(y = bottomBarOffset)
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = stringResource(id = item.title)
                                )
                            },
                            label = {
                                Text(
                                    stringResource(id = item.title),
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            },
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = Color.Gray,
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            alwaysShowLabel = false,
                            onClick = {
                                navController.navigateSingleTopToGraph(item.route)
                            }
                        )
                    }
                }
            } else {
                // To Do : In case of Tablet, you've to implement here
            }
        },
        content = { innerPadding ->
            BoxWithConstraints(
                Modifier.padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    top = 0.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = if (bottomBarVisible)
                        innerPadding.calculateBottomPadding()
                    else
                        0.dp)
            ) {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = photosHomeRoute
                ) {
                    photoGraph(
                        navController = navController,
                        startDestination =  photosStartRoute,
                        route = photosHomeRoute
                    )
                    communityGraph(
                        navController = navController,
                        startDestination = communitiesStartRoute,
                        route = communityHomeRoute
                    )
                    notificationGraph(
                        navController = navController,
                        startDestination = notificationsStartRoute,
                        route =  notificationHomeRoute
                    )
                    settingGraph(
                        navController = navController,
                        startDestination = settingStartRoute,
                        route = settingHomeRoute
                    )
                }
            }
        }
    )

    navController.addOnDestinationChangedListener { _, destination, _ ->
        bottomBarVisible = when(destination.route) {
            photosStartRoute, communitiesStartRoute, notificationsStartRoute, settingStartRoute -> {
                true
            }

            else -> {
                false
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    showSystemUi = true
)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProfilerHomeScreenPreview(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController()
) {
    PhogalTheme {
        Scaffold(
            bottomBar = {
                val items = listOf(
                    BottomNavItem.Photo,
                    BottomNavItem.Community,
                    BottomNavItem.Notification,
                    BottomNavItem.Setting,
                )

                BottomNavigation(
                    backgroundColor = ColorBottomBar,
                    contentColor = Color(0xFF3F414E),
                    elevation = 5.dp,
                    modifier = Modifier
                        .offset(y = 0.dp)
                        .navigationBarsPadding()
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = stringResource(id = item.title)
                                )
                            },
                            label = {
                                Text(
                                    stringResource(id = item.title),
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 8.sp
                                )
                            },
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = Color.Gray,
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            alwaysShowLabel = true,
                            onClick = {
                                navController.navigateSingleTopToGraph(item.route)
                            }
                        )
                    }
                }
            },
            content = { innerPadding ->
                AnimatedNavHost(
                    navController = navController,
                    startDestination = photosHomeRoute,
                    modifier = modifier.padding(0.dp, 0.dp, 0.dp, innerPadding.calculateBottomPadding()),
                ) {
                    photoGraph(
                        navController = navController,
                        startDestination =  photosStartRoute,
                        route = photosStartRoute
                    )
                    communityGraph(
                        navController = navController,
                        startDestination = communitiesStartRoute,
                        route = communityHomeRoute
                    )
                    notificationGraph(
                        navController = navController,
                        startDestination = notificationsStartRoute,
                        route = notificationHomeRoute
                    )
                    settingGraph(
                        navController = navController,
                        startDestination = settingStartRoute,
                        route = settingHomeRoute
                    )
                }
            }
        )
    }
}