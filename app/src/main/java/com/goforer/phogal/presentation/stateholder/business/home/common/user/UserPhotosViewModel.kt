package com.goforer.phogal.presentation.stateholder.business.home.common.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.ApiEmptyResponse
import com.goforer.phogal.data.datasource.network.response.Status
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.data.repository.common.user.photos.GetUserPhotosRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPhotosViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserPhotosRepository: GetUserPhotosRepository
) : BaseViewModel<PhotoUiState>() {
    private val _uiState = MutableStateFlow(Any())
    val uiState: StateFlow<Any> = _uiState
    val isRefreshing = MutableStateFlow(false)

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            getUserPhotosRepository.trigger(
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
        getUserPhotosRepository.invalidatePagingSource()
    }
}