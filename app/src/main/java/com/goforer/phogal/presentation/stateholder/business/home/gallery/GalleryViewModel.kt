package com.goforer.phogal.presentation.stateholder.business.home.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.ApiEmptyResponse
import com.goforer.phogal.data.datasource.network.response.Status
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.data.repository.gallery.GetPhotosRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPhotosRepository: GetPhotosRepository
) : BaseViewModel<PhotoUiState>() {
    private val _uiState = MutableStateFlow(Any())
    val uiState: StateFlow<Any> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            getPhotosRepository.trigger(
                replyCount = replyCount,
                params = params
            ).cachedIn(viewModelScope)
            .onStart {
                Status.LOADING
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ApiEmptyResponse<PagingData<PhotoUiState>>(),
            ).collectLatest {
                _uiState.value = it
            }
        }
    }

    override fun invalidate() {
        getPhotosRepository.invalidatePagingSource()
    }
}