package com.goforer.phogal.presentation.stateholder.business.home.common.photo.info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.Status
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.data.repository.common.photo.info.GetPictureRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPictureRepository: GetPictureRepository
) : BaseViewModel<PhotoUiState>() {
    private val _uiState = MutableStateFlow(Any())
    val uiState: StateFlow<Any> = _uiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            getPictureRepository.trigger(
                replyCount = replyCount,
                params = params
            )
                .onStart {
                    Status.LOADING
                }.stateIn(
                    scope = viewModelScope
                ).collectLatest {
                    _uiState.value = handleResponse(it)
                }
        }
    }
}