package com.goforer.phogal.presentation.ui.navigation.destination

import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.goforer.phogal.presentation.ui.compose.screen.home.photo.PhotosScreen
import com.goforer.phogal.presentation.ui.navigation.destination.PhogalDestination.Companion.photosStartRoute
import timber.log.Timber

object Photos : PhogalDestination {
    override val icon = Icons.Sharp.Person
    override val route = photosStartRoute
    override val screen: @Composable (navController: NavHostController, bundle: Bundle?) -> Unit = { navController, _ ->
        navController.currentBackStackEntry?.let {
            PhotosScreen(
                onItemClicked = { item, index ->
                    Timber.d("${item.title}${" - "}${"Index - "}${index}")
                }
            )
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