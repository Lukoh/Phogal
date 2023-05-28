package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.userphotos

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.goforer.base.designsystem.component.CardSnackBar
import com.goforer.phogal.R
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPhotosScreen(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    name: String,
    firstName: String,
    onItemClicked: (id: String) -> Unit,
    onBackPressed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

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
                        text = "${firstName}${" "}${stringResource(id = R.string.picture_photos)}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
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
            UserPhotosContent(
                modifier = modifier,
                navBackStackEntry = navBackStackEntry,
                name,
                contentPadding = paddingValues,
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