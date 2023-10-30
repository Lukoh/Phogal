package com.goforer.phogal.presentation.stateholder.business.home.popularphotos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.data.repository.popularphotos.GetPopularPhotosRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularPhotosViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPopularPhotosRepository: GetPopularPhotosRepository
) : BaseViewModel<PhotoUiState>() {
    val popularPhotosUiState = MutableStateFlow(Any())
    val isRefreshing = MutableStateFlow(false)

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            getPopularPhotosRepository.trigger(
                replyCount = replyCount,
                params = params
            ).cachedIn(viewModelScope)
                .stateIn(viewModelScope)
                .collectLatest {
                    popularPhotosUiState.value = it
                }
        }
    }

    override fun invalidate() {
        getPopularPhotosRepository.invalidatePagingSource()
    }
}