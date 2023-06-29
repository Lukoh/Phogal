package com.goforer.phogal.presentation.stateholder.business.home.common.photo.like

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.model.remote.response.gallery.photo.like.LikeResponse
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.repository.common.photo.like.DeletePictureLikeRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureUnlikeViewModel
@Inject constructor(
    private val deletePictureLikeRepository: DeletePictureLikeRepository
) : BaseViewModel<LikeResponse>() {
    private val _unLikeUiState = MutableStateFlow(Any())
    val unlikeUiState: StateFlow<Any> = _unLikeUiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            deletePictureLikeRepository.trigger(
                replyCount = replyCount,
                params = params
            ).stateIn(viewModelScope)
                .collectLatest {
                    val response = handleResponse(it)

                    _unLikeUiState.value = response
                }
        }
    }
}