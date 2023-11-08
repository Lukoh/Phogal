package com.goforer.phogal.presentation.stateholder.business.home.common.photo.like

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.model.remote.response.gallery.photo.like.LikeResponseUiState
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.repository.common.photo.like.PostPictureLikeRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureLikeViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postPictureLikeRepository: PostPictureLikeRepository
) : BaseViewModel<LikeResponseUiState>() {
    private val _uiState = MutableStateFlow(Any())
    val uiState: StateFlow<Any> = _uiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            postPictureLikeRepository.trigger(
                replyCount = replyCount,
                params = params
            ).stateIn(
                scope = viewModelScope
            ).collectLatest {
                _uiState.value = handleResponse(it)
            }
        }
    }
}