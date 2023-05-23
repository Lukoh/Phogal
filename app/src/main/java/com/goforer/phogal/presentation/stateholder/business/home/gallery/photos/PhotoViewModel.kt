package com.goforer.phogal.presentation.stateholder.business.home.gallery.photos

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.repository.gallery.photos.GetImagesRepository
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
class PhotoViewModel
@Inject constructor(
    private val getImagesRepository: GetImagesRepository
) : BaseViewModel() {
    private val _photosUiState = MutableStateFlow(Any())
    val photosUiState: StateFlow<Any> = _photosUiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
           getImagesRepository.trigger(
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