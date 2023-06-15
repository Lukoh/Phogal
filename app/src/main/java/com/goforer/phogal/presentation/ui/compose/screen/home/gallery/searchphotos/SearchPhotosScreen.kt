package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.searchphotos

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.goforer.base.designsystem.component.CardSnackBar
import com.goforer.phogal.R
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photos.GalleryViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.SearchPhotosContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberSearchPhotosContentState
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPhotosScreen(
    modifier: Modifier = Modifier,
    galleryViewModel: GalleryViewModel,
    state: SearchPhotosContentState = rememberSearchPhotosContentState(),
    onItemClicked: (id: String) -> Unit,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onOpenWebView: (firstName: String, url: String) -> Unit,
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

    DisposableEffect(state.baseUiState.lifecycle) {
        // Create an observer that triggers our remembered callbacks
        // for doing anything
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        // Add the observer to the lifecycle
        state.baseUiState.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            state.baseUiState.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        contentColor = ColorBgSecondary,
        snackbarHost = { SnackbarHost(
            snackbarHostState, snackbar = { snackbarData: SnackbarData ->
                CardSnackBar(modifier = Modifier, snackbarData)
            }
        )
        }, topBar = {
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
        }, content = { paddingValues ->
            SearchPhotosContent(
                modifier = modifier,
                galleryViewModel = galleryViewModel,
                photosContentState = state,
                contentPadding = paddingValues,
                onItemClicked = onItemClicked,
                onViewPhotos = onViewPhotos,
                onShowSnackBar = {
                    state.baseUiState.scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                },
                onOpenWebView = onOpenWebView
            )
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
fun SearchPhotosScreenPreview() {
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