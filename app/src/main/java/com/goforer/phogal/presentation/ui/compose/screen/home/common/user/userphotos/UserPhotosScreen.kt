package com.goforer.phogal.presentation.ui.compose.screen.home.common.user.userphotos

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goforer.base.designsystem.component.CardSnackBar
import com.goforer.base.designsystem.component.CustomCenterAlignedTopAppBar
import com.goforer.base.designsystem.component.ScaffoldContent
import com.goforer.phogal.R
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.model.local.home.gallery.NameArgument
import com.goforer.phogal.data.repository.Repository
import com.goforer.phogal.presentation.stateholder.business.home.common.user.UserPhotosViewModel
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.photos.rememberUserPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.compose.screen.HandleObserver
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun UserPhotosScreen(
    modifier: Modifier = Modifier,
    userPhotosViewModel: UserPhotosViewModel = hiltViewModel(),
    nameArgument: NameArgument,
    state: BaseUiState = rememberBaseUiState(),
    onItemClicked: (id: String) -> Unit,
    onBackPressed: () -> Unit,
    onStart: () -> Unit = {
        //To Do:: Implement the code what you want to do....
    },
    onStop: () -> Unit = {
        //To Do:: Implement the code what you want to do....
    }
) {
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)
    val snackbarHostState = remember { SnackbarHostState() }
    val backHandlingEnabled by remember { mutableStateOf(true) }
    var visibleActions by rememberSaveable { mutableStateOf(true) }

    BackHandler(backHandlingEnabled) {
        onBackPressed()
    }

    HandleObserver(
        lifecycle = state.lifecycle,
        onStart = currentOnStart,
        onStop = currentOnStop
    )
    Scaffold(
        contentColor = ColorBgSecondary,
        snackbarHost = { SnackbarHost(
            snackbarHostState, snackbar = { snackbarData: SnackbarData ->
                CardSnackBar(modifier = Modifier, snackbarData)
            }
        )
        }, topBar = {
            CustomCenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "${nameArgument.firstName}${" "}${stringResource(id = R.string.picture_photos)}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackPressed()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (visibleActions) {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite"
                            )
                        }
                    }
                }
            )
        }, content = { paddingValues ->
            ScaffoldContent(topInterval = 2.dp) {
                UserPhotosContent(
                    modifier = modifier,
                    state = rememberUserPhotosContentState(
                        uiState = userPhotosViewModel.uiState,
                        refreshingState = userPhotosViewModel.isRefreshing
                    ),
                    contentPadding = paddingValues,
                    onTriggered = {
                        if (it)
                            userPhotosViewModel.trigger(
                                1,
                                Params(nameArgument.name, Repository.ITEM_COUNT)
                            )
                    },
                    onItemClicked = {
                        onItemClicked(it)
                    },
                    onShowSnackBar = {
                        state.scope.launch {
                            snackbarHostState.showSnackbar(it)
                        }
                    },
                    onSuccess = { isSuccessful ->
                        visibleActions = isSuccessful
                    }
                )
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    showSystemUi = true
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosScreenPreview() {
    PhogalTheme {
        Scaffold(
            contentColor = Color.White,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.app_name),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Profile"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                )
            }
        ) {
        }
    }
}