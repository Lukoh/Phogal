package com.goforer.phogal.presentation.stateholder.business.home.common.photo.like

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.model.remote.response.gallery.photo.like.LikeResponse
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
    private val postPictureLikeRepository: PostPictureLikeRepository
) : BaseViewModel<LikeResponse>() {
    private val _likeUiState = MutableStateFlow(Any())
    val likeUiState: StateFlow<Any> = _likeUiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            postPictureLikeRepository.trigger(
                replyCount = replyCount,
                params = params
            ).stateIn(viewModelScope)
                .collectLatest {
                    val response = handleResponse(it)

                    _likeUiState.value = response
                }
        }
    }
}