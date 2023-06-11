package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo

import com.goforer.base.designsystem.component.dialog.ErrorDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goforer.base.designsystem.component.CardSnackBar
import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.R
import com.goforer.phogal.data.model.local.error.Errors
import com.goforer.phogal.data.model.remote.response.gallery.photo.like.LikeResponse
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photo.info.PictureViewModel
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photo.like.PictureLikeViewModel
import com.goforer.phogal.presentation.stateholder.business.home.gallery.photo.like.PictureUnlikeViewModel
import com.goforer.phogal.presentation.stateholder.uistate.BaseUiState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.photo.PhotoContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.photo.rememberPhotoContentState
import com.goforer.phogal.presentation.stateholder.uistate.rememberBaseUiState
import com.goforer.phogal.presentation.ui.theme.Red60
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PictureScreen(
    modifier: Modifier = Modifier,
    pictureViewModel: PictureViewModel,
    likeViewModel: PictureLikeViewModel,
    unLikeViewModel: PictureUnlikeViewModel,
    storage: LocalStorage,
    baseUiState: BaseUiState = rememberBaseUiState(),
    id: String,
    visibleViewPhotosButton: Boolean,
    state: PhotoContentState = rememberPhotoContentState(
        visibleViewPhotosButton = rememberSaveable { mutableStateOf(visibleViewPhotosButton) }
    ),
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
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

    val showDialogState = rememberSaveable { mutableStateOf(false) }

    LikeResponseHandle(likeViewModel = likeViewModel, showDialogState = showDialogState)
    UnlikeResponseHandle(unLikeViewModel = unLikeViewModel, showDialogState = showDialogState)
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
                    if (state.visibleActions.value)
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = if (state.enabledLike.value)
                                    Red60
                                else
                                    Color.Black,
                            ),
                            onClick = {
                                showDialogState.value = true
                                if (!state.picture?.liked_by_user!!)
                                    likeViewModel.trigger(2, Params(id))
                                else
                                    unLikeViewModel.trigger(2, Params(id))
                            }
                        ) {
                            state.picture?.liked_by_user?.let { liked ->
                                Icon(
                                    imageVector = if (liked)
                                        ImageVector.vectorResource(id = R.drawable.ic_like_on)
                                    else
                                        ImageVector.vectorResource(id = R.drawable.ic_like_off),
                                    contentDescription = "Like"
                                )
                            }
                        }

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
                                contentDescription = "Bookmark"
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
                    state.visibleActions.value = true
                    state.enabledBookmark.value = storage.isPhotoBookmarked(it)
                }
            )
        }
    )
}

@Composable
fun LikeResponseHandle(
    likeViewModel: PictureLikeViewModel,
    state: PhotoContentState = rememberPhotoContentState(),
    showDialogState: MutableState<Boolean>
) {
    val likeUiState = likeViewModel.likeUiState.collectAsStateWithLifecycle()

    if (likeUiState.value is Resource) {
        val resource = likeUiState.value as Resource
        when(resource.status) {
            Status.SUCCESS -> {
                val likeResponse = resource.data as LikeResponse

                state.enabledLike.value = likeResponse.photo.liked_by_user
                Timber.d("Like Success : %s", state.enabledLike.value.toString())
            }
            Status.LOADING -> {

            }
            Status.ERROR-> {
                state.enabledLike.value = false
                Timber.d("Like Failed : %s", state.enabledLike.value.toString())
                if (showDialogState.value) {
                    AnimatedVisibility(
                        visible = true,
                        modifier = Modifier,
                        enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                                fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                        exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                                fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
                    ) {
                        ErrorDialog(
                            title = if (resource.errorCode !in 200..299)
                                stringResource(id = R.string.error_dialog_network_title)
                            else
                                stringResource(id = R.string.error_dialog_title),
                            text = (if (resource.message?.contains("errors")!!)
                                Gson().fromJson(resource.message.toString(), Errors::class.java).errors[0]
                            else
                                resource.message.toString())
                        ) {
                            showDialogState.value = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UnlikeResponseHandle(
    unLikeViewModel: PictureUnlikeViewModel,
    state: PhotoContentState = rememberPhotoContentState(),
    showDialogState: MutableState<Boolean>
) {
    val unlikeUiState = unLikeViewModel.unlikeUiState.collectAsStateWithLifecycle()

    if (unlikeUiState.value is Resource) {
        val resource = unlikeUiState.value as Resource
        when(resource.status) {
            Status.SUCCESS -> {
                val likeResponse = resource.data as LikeResponse

                state.enabledLike.value = likeResponse.photo.liked_by_user
                Timber.d("Like Success : %s", state.enabledLike.value.toString())
            }
            Status.LOADING -> {

            }
            Status.ERROR-> {
                state.enabledLike.value = true
                Timber.d("Unlike Failed : %s", state.enabledLike.value.toString())
                if (showDialogState.value) {
                    AnimatedVisibility(
                        visible = true,
                        modifier = Modifier,
                        enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                                fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                        exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                                fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
                    ) {
                        ErrorDialog(
                            title = if (resource.errorCode !in 200..299)
                                stringResource(id = R.string.error_dialog_network_title)
                            else
                                stringResource(id = R.string.error_dialog_title),
                            text = (if (resource.message?.contains("errors")!!)
                                Gson().fromJson(resource.message.toString(), Errors::class.java).errors[0]
                            else
                                resource.message.toString())
                        ) {
                            showDialogState.value = false
                        }
                    }
                }
            }
        }
    }
}