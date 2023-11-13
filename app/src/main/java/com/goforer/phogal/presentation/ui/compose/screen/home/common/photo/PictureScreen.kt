package com.goforer.phogal.presentation.ui.compose.screen.home.common.photo

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goforer.base.designsystem.component.CardSnackBar
import com.goforer.base.designsystem.component.CustomCenterAlignedTopAppBar
import com.goforer.base.designsystem.component.ScaffoldContent
import com.goforer.phogal.R
import com.goforer.phogal.data.model.local.error.Errors
import com.goforer.phogal.data.model.remote.response.gallery.photo.like.LikeResponseUiState
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.Status
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.presentation.stateholder.business.home.common.bookmark.BookmarkViewModel
import com.goforer.phogal.presentation.stateholder.business.home.common.photo.info.PictureViewModel
import com.goforer.phogal.presentation.stateholder.business.home.common.photo.like.PictureLikeViewModel
import com.goforer.phogal.presentation.stateholder.business.home.common.photo.like.PictureUnlikeViewModel
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.PhotoContentState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.photo.rememberPhotoContentState
import com.goforer.phogal.presentation.ui.compose.screen.HandleObserver
import com.goforer.phogal.presentation.ui.theme.Red60
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureScreen(
    modifier: Modifier = Modifier,
    pictureViewModel: PictureViewModel = hiltViewModel(),
    likeViewModel: PictureLikeViewModel = hiltViewModel(),
    unLikeViewModel: PictureUnlikeViewModel =  hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    state: PhotoContentState = rememberPhotoContentState(),
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onBackPressed: () -> Unit,
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
    val backHandlingEnabled by remember { mutableStateOf(true) }

    BackHandler(backHandlingEnabled) {
       onBackPressed()
    }

    HandleObserver(
        lifecycle = state.baseUiState.lifecycle,
        onStart = currentOnStart,
        onStop = currentOnStop
    )
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
            CustomCenterAlignedTopAppBar(
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
                            state.enabledLoadState.value = false
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
                    if (state.visibleActionsState.value) {
                        HandleLike(
                            enabledLike = state.enabledLikeState.value,
                            showDialogState = showDialogState,
                            likedByUser = state.photoUiState?.liked_by_user!!,
                            onTriggered = {
                                if (it)
                                    likeViewModel.trigger(1, Params(state.idState.value))
                                else
                                    unLikeViewModel.trigger(1, Params(state.idState.value))
                            }
                        )
                        HandleBookmark(
                            enabledBookmark = state.enabledBookmarkState.value,
                            photoUiState = state.photoUiState,
                            enabledBookmarkState = state.enabledBookmarkState,
                            onBookmarked = {
                                bookmarkViewModel.setBookmarkPicture(it)
                            }
                        )
                    }
                }
            )
        }, content = { paddingValues ->
            ScaffoldContent(topInterval = 0.dp) {
                PictureContent(
                    modifier = modifier,
                    contentPadding = paddingValues,
                    state = rememberPhotoContentState(
                        uiState = pictureViewModel.uiState
                    ),
                    onTriggered = {
                        if (it)
                            pictureViewModel.trigger(1, Params(state.idState.value))
                    },
                    onViewPhotos = onViewPhotos,
                    onShowSnackBar = {
                        state.baseUiState.scope.launch {
                            snackbarHostState.showSnackbar(it)
                        }
                    },
                    onShownPhoto = {
                        state.photoUiState = it
                        state.visibleActionsState.value = true
                        state.enabledBookmarkState.value =  bookmarkViewModel.isPhotoBookmarked(it.id, it.urls.raw)
                    },
                    onOpenWebView = onOpenWebView,
                    onSuccess = { isSuccessful ->
                        if (!isSuccessful)
                            state.visibleActionsState.value = false
                    }
                )
            }
        }
    )
}

@Composable
fun HandleLike(
    enabledLike: Boolean,
    showDialogState: MutableState<Boolean>,
    likedByUser: Boolean,
    onTriggered: (likedByUser: Boolean) -> Unit
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (enabledLike)
                Red60
            else
                Color.Black,
        ),
        onClick = {
            showDialogState.value = true
            if (!likedByUser)
                onTriggered(true)
            else
                onTriggered(false)
        }
    ) {
        Icon(
            imageVector = if (likedByUser)
                ImageVector.vectorResource(id = R.drawable.ic_like_on)
            else
                ImageVector.vectorResource(id = R.drawable.ic_like_off),
            contentDescription = "Like"
        )
    }
}

@Composable
fun HandleBookmark(
    enabledBookmark: Boolean,
    photoUiState: PhotoUiState?,
    enabledBookmarkState: MutableState<Boolean>,
    onBookmarked: (photoUiState: PhotoUiState) -> Unit
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (enabledBookmark)
                Red60
            else
                Color.Black,
        ),
        onClick = {
            photoUiState?.let {
                onBookmarked(it)
            }

            enabledBookmarkState.value = !enabledBookmarkState.value
        }
    ) {
        Icon(
            imageVector = if (enabledBookmark)
                ImageVector.vectorResource(id = R.drawable.ic_bookmark_on)
            else
                ImageVector.vectorResource(id = R.drawable.ic_bookmark_off),
            contentDescription = "Bookmark"
        )
    }
}

@Composable
fun LikeResponseHandle(
    likeViewModel: PictureLikeViewModel,
    state: PhotoContentState = rememberPhotoContentState(),
    showDialogState: MutableState<Boolean>
) {
    val resource by likeViewModel.uiState.collectAsStateWithLifecycle()

    when(resource.status) {
        Status.SUCCESS -> {
            val likeResponseUiState = resource.data as LikeResponseUiState

            state.enabledLikeState.value = likeResponseUiState.photo.liked_by_user
            Timber.d("Like Success : %s", state.enabledLikeState.value.toString())
        }
        Status.LOADING -> {}
        Status.ERROR-> {
            state.enabledLikeState.value = false
            Timber.d("Like Failed : %s", state.enabledLikeState.value.toString())
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

@Composable
fun UnlikeResponseHandle(
    unLikeViewModel: PictureUnlikeViewModel,
    state: PhotoContentState = rememberPhotoContentState(),
    showDialogState: MutableState<Boolean>
) {
    val resource by unLikeViewModel.uiState.collectAsStateWithLifecycle()

    when(resource.status) {
        Status.SUCCESS -> {
            val likeResponseUiState = resource.data as LikeResponseUiState

            state.enabledLikeState.value = likeResponseUiState.photo.liked_by_user
            Timber.d("Like Success : %s", state.enabledLikeState.value.toString())
        }
        Status.LOADING -> {}
        Status.ERROR-> {
            state.enabledLikeState.value = true
            Timber.d("Unlike Failed : %s", state.enabledLikeState.value.toString())
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