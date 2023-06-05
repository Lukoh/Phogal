package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.goforer.base.designsystem.component.CardSnackBar
import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.R
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photo.PictureViewModel
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.photo.PhotoContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.photo.rememberPhotoContentState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.theme.Red60
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PictureScreen(
    modifier: Modifier = Modifier,
    pictureViewModel: PictureViewModel,
    storage: LocalStorage,
    baseUiState: BaseUiState = rememberBaseUiState(),
    id: String,
    visibleViewPhotosButton: Boolean,
    state: PhotoContentState = rememberPhotoContentState(
        visibleViewPhotosButton = rememberSaveable { mutableStateOf(visibleViewPhotosButton) }
    ),
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onBackPressed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        contentColor = Color.White,
        snackbarHost = {
            SnackbarHost(
                snackbarHostState, snackbar = { snackbarData: SnackbarData ->
                    CardSnackBar(modifier = Modifier, snackbarData)
                }
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.picture_title),
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
                            state.enabledLoadPhotos.value = false
                            onBackPressed()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Picture"
                        )
                    }
                },
                actions = {
                    if (state.visibleBookmark.value)
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = if (state.enabledBookmark.value)
                                    Red60
                                else
                                    Color.Black,
                            ),
                            onClick = {
                                state.picture?.let {
                                    storage.setBookmarkPhoto(it)
                                }

                                state.enabledBookmark.value = !state.enabledBookmark.value
                            }
                        ) {
                            Icon(
                                imageVector = if (state.enabledBookmark.value)
                                    ImageVector.vectorResource(id = R.drawable.ic_bookmark_on)
                                else
                                    ImageVector.vectorResource(id = R.drawable.ic_bookmark_off),
                                contentDescription = "Favorite"
                            )
                        }
                }
            )
        }, content = { paddingValues ->
            PictureContent(
                modifier = modifier,
                contentPadding = paddingValues,
                pictureViewModel = pictureViewModel,
                id = id,
                state = state,
                onViewPhotos = onViewPhotos,
                onShowSnackBar = {
                    baseUiState.scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                },
                onShownPhoto = {
                    state.picture = it
                    state.visibleBookmark.value = true
                    state.enabledBookmark.value = storage.isPhotoBookmarked(it)
                }
            )
        }
    )
}