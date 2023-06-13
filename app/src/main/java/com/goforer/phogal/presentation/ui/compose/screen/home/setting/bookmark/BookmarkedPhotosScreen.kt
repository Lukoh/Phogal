package com.goforer.phogal.presentation.ui.compose.screen.home.setting.bookmark

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Bookmark
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BookmarkedPhotosScreen(
    modifier: Modifier = Modifier,
    baseUiState: BaseUiState = rememberBaseUiState(),
    onItemClicked: (item: Picture, index: Int) -> Unit,
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
    val enabledLoadPhotosState = remember { mutableStateOf(true) }

    DisposableEffect(baseUiState.lifecycle) {
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
        baseUiState.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            baseUiState.lifecycle.removeObserver(observer)
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
                        text = stringResource(id = R.string.setting_bookmarked_photos),
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
                            enabledLoadPhotosState.value = false
                            onBackPressed()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        }, content = { paddingValues ->
            BookmarkedPhotosContent(
                modifier = modifier,
                contentPadding = paddingValues,
                enabledLoadPhotosState = enabledLoadPhotosState,
                onItemClicked = onItemClicked
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
fun  BookmarkedPhotosScreenPreview() {
    PhogalTheme {
        Scaffold(
            contentColor = Color.White,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.setting_bookmarked_photos),
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
                                imageVector = Icons.Filled.ArrowBackIos,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.Bookmark,
                                contentDescription = "Bookmark"
                            )
                        }
                    }
                )
            }
        ) {
        }
    }
}