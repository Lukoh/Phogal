package com.goforer.phogal.presentation.stateholder.business.home.common.gallery.bookmark

import androidx.lifecycle.viewModelScope
import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel
@Inject
constructor() : BaseViewModel<Picture>() {
    @Inject
    lateinit var localStorage: LocalStorage

    private val _bookmarkPhotosUiState = MutableStateFlow(mutableListOf<Picture>())
    val bookmarkPhotosUiState: StateFlow<MutableList<Picture>> = _bookmarkPhotosUiState

    fun setBookmarkPicture(picture: Picture) {
        localStorage.setBookmarkPhoto(picture)
    }

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            flowOf(
                localStorage.geBookmarkedPhotos()
            ).stateIn(viewModelScope)
             .collectLatest { users ->
                 _bookmarkPhotosUiState.value = users ?: mutableListOf()
             }
        }
    }

    fun isPhotoBookmarked(picture: Picture) = localStorage.isPhotoBookmarked(picture)
}