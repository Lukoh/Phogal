package com.goforer.phogal.presentation.stateholder.business.home.gallery.user

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.repository.gallery.user.photos.GetUserPhotosRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPhotosViewModel
@Inject constructor(
    private val getUserPhotosRepository: GetUserPhotosRepository
) : BaseViewModel<Photo>() {
    private val _photosUiState = MutableStateFlow(Any())
    val photosUiState: StateFlow<Any> = _photosUiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            getUserPhotosRepository.trigger(
                replyCount = replyCount,
                params = params
            ).cachedIn(viewModelScope)
                .stateIn(viewModelScope)
                .collectLatest {
                    _photosUiState.value = it
                }
        }
    }
}