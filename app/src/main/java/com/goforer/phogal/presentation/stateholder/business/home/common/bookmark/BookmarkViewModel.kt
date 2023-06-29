package com.goforer.phogal.presentation.stateholder.business.home.common.bookmark

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.datasource.local.LocalDataSource
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.data.datasource.network.api.Params
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
constructor(
    private val localDataSource: LocalDataSource
) : BaseViewModel<Picture>() {
    private val _bookmarkPhotosUiState = MutableStateFlow(mutableListOf<Picture>())
    val bookmarkPhotosUiState: StateFlow<MutableList<Picture>> = _bookmarkPhotosUiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            flowOf(
                localDataSource.geBookmarkedPhotos()
            ).stateIn(viewModelScope)
             .collectLatest { users ->
                 _bookmarkPhotosUiState.value = users ?: mutableListOf()
             }
        }
    }

    fun setBookmarkPicture(picture: Picture) {
        localDataSource.setBookmarkPhoto(picture)
    }

    fun isPhotoBookmarked(picture: Picture) = localDataSource.isPhotoBookmarked(picture)

    fun isPhotoBookmarked(id: String) = localDataSource.isPhotoBookmarked(id)
}