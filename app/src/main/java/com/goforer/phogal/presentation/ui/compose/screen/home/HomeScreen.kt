package com.goforer.phogal.presentation.ui.compose.screen.home

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.goforer.phogal.R
import com.goforer.phogal.presentation.stateholder.uistate.MainScreenState
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationHomeRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.notificationsStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.photosHomeRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.popularPhotosHomeRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.popularPhotosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.searchPhotosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingHomeRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.settingStartRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateSingleTopToGraph
import com.goforer.phogal.presentation.ui.navigation.graph.galleryGraph
import com.goforer.phogal.presentation.ui.navigation.graph.notificationGraph
import com.goforer.phogal.presentation.ui.navigation.graph.popularPhotosGraph
import com.goforer.phogal.presentation.ui.navigation.graph.settingGraph
import com.goforer.phogal.presentation.ui.theme.Blue80
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.ColorBlackLight
import com.goforer.phogal.presentation.ui.theme.ColorBottomBar
import com.goforer.phogal.presentation.ui.theme.ColorSnowWhite
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import timber.log.Timber

@Stable
sealed class BottomNavDestination(val route: String, @DrawableRes val icon: Int, @StringRes val title: Int) {
    object Gallery : BottomNavDestination(photosHomeRoute, R.drawable.ic_photo, R.string.bottom_navigation_photo)
    object PopularPhotos :  BottomNavDestination(popularPhotosHomeRoute, R.drawable.ic_popphotos, R.string.bottom_navigation_popular_photos)
    object Notification :  BottomNavDestination(notificationHomeRoute, R.drawable.ic_notification, R.string.bottom_navigation_notification)
    object Setting : BottomNavDestination(settingHomeRoute, R.drawable.ic_setting, R.string.bottom_navigation_setting)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState
) {
    var bottomBarVisible by remember { mutableStateOf(false) }
    val bottomBarOffset by animateDpAsState(targetValue = if (bottomBarVisible) 0.dp else 56.dp)
    var currentRoute by rememberSaveable { mutableStateOf("") }

    Scaffold(
        containerColor = ColorBgSecondary,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (state.shouldShowBottomBar) {
                val items = listOf(
                    BottomNavDestination.Gallery,
                    BottomNavDestination.PopularPhotos,
                    BottomNavDestination.Notification,
                    BottomNavDestination.Setting,
                )

                BottomNavigation(
                    backgroundColor = ColorBottomBar,
                    contentColor = Blue80,
                    elevation = 5.dp,
                    modifier = if (bottomBarVisible)
                        modifier.navigationBarsPadding()
                    else
                        modifier.offset(y = bottomBarOffset),
                ) {
                    items.forEach { item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = stringResource(id = item.title),
                                    tint = ColorSnowWhite
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(id = item.title),
                                    color = ColorSnowWhite,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            },
                            selectedContentColor = Blue80,
                            unselectedContentColor = Color.Gray,
                            selected = state.currentDestination?.hierarchy?.any { it.route.equals(item.route) } == true,
                            alwaysShowLabel = false,
                            onClick = {
                                if (item.route == photosHomeRoute && item.route == currentRoute) {
                                    Timber.d("route is identical")
                                } else {
                                    state.navigateToTopLevelDestination(item)
                                }

                                currentRoute = item.route
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
                NavHost(
                    navController = state.navController,
                    startDestination = photosHomeRoute
                ) {
                    galleryGraph(
                        navController = state.navController,
                        startDestination = searchPhotosStartRoute,
                        route = photosHomeRoute
                    )
                    popularPhotosGraph(
                        navController = state.navController,
                        startDestination = popularPhotosStartRoute,
                        route = popularPhotosHomeRoute
                    )
                    notificationGraph(
                        navController = state.navController,
                        startDestination = notificationsStartRoute,
                        route =  notificationHomeRoute
                    )
                    settingGraph(
                        navController = state.navController,
                        startDestination = settingStartRoute,
                        route = settingHomeRoute
                    )
                }
            }
        }
    )

    state.navController.addOnDestinationChangedListener { _, destination, _ ->
        bottomBarVisible = when(destination.route) {
            searchPhotosStartRoute, popularPhotosStartRoute, notificationsStartRoute, settingStartRoute -> {
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
@Composable
fun ProfilerHomeScreenPreview(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    PhogalTheme {
        Scaffold(
            bottomBar = {
                val items = listOf(
                    BottomNavDestination.Gallery,
                    BottomNavDestination.PopularPhotos,
                    BottomNavDestination.Notification,
                    BottomNavDestination.Setting,
                )

                BottomNavigation(
                    backgroundColor = ColorBottomBar,
                    contentColor = Blue80,
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
                            selectedContentColor = Blue80,
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
                NavHost(
                    navController = navController,
                    startDestination = photosHomeRoute,
                    modifier = modifier.padding(0.dp, 0.dp, 0.dp, innerPadding.calculateBottomPadding())
                ) {
                    galleryGraph(
                        navController = navController,
                        startDestination =  searchPhotosStartRoute,
                        route = photosHomeRoute
                    )
                    popularPhotosGraph(
                        navController = navController,
                        startDestination = popularPhotosStartRoute,
                        route = popularPhotosHomeRoute
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