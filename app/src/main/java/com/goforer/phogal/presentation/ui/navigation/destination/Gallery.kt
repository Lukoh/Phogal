package com.goforer.phogal.presentation.ui.navigation.destination

import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.sharp.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo.PictureScreen
import com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photos.PhotosScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.photosStartRoute
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.pictureRoute
import com.goforer.phogal.presentation.ui.navigation.ext.navigateSingleTopTo

object Gallery : PhogalDestination {
    override val icon = Icons.Sharp.ViewList
    override val route = photosStartRoute
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, _ ->
        PhotosScreen(
            onItemClicked = { id ->
                navController.navigateSingleTopTo("${Picture.route}/$id")
            }
        )
    }
}

object Picture : PhogalDestination {
    override val icon = Icons.Filled.Photo
    override val route = pictureRoute
    private const val idTypeArg = "id"
    val routeWithArgs = "$route/{$idTypeArg}"
    val arguments = listOf(
        navArgument(idTypeArg) { type = NavType.StringType }
    )

    @Stable
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, bundle ->
        navController.previousBackStackEntry?.let {
            val id = bundle?.getString(idTypeArg)

            id?.let {
                PictureScreen(
                    id = it,
                    onBackPressed = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

/*
object DetailInfo : PhogalDestination {
    override val icon = Icons.Filled.Details
    override val route = detailInfoRoute
    private const val idTypeArg = "user_id"
    val routeWithArgs = "$route/{$idTypeArg}"
    val arguments = listOf(
        navArgument(idTypeArg) { type = NavType.IntType }
    )

    @Stable
    @RequiresApi(Build.VERSION_CODES.N)
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, bundle ->
        navController.previousBackStackEntry?.let {
            val viewModel: PhotoViewModel =  hiltViewModel(it)
            val id = bundle?.getInt(idTypeArg)

            id?.let { userId ->
                DetailScreen(
                    state = rememberDetailContentState(
                        uiStateFlow = viewModel.resourceStateFlow,
                        onGetMember =  viewModel::getMember
                    ),
                    userId = userId,
                    onMembersClicked = {
                        navController.navigateSingleTopTo(Members.route)
                    },
                    onBackPressed = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
object Viewer : PhogalDestination {
    override val icon = Icons.Sharp.People
    override val route = viewerRoute
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, _ ->
        navController.previousBackStackEntry?.let {
            val viewModel: ViewerViewModel =  hiltViewModel(it)

            ViewerScreen(
                state = rememberMembersContentState(
                    baseUiState = rememberBaseUiState(
                        resourceStateFlow = viewModel.resourceStateFlow
                    ),
                    onGetMembers = viewModel::getMembers,
                    onGetMember = viewModel::getMember,
                    onEstimated = viewModel::setEstimation
                ),
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}

 */