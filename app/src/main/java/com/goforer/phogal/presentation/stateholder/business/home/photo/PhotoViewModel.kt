package com.goforer.phogal.presentation.stateholder.business.home.photo

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.Resource
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.data.repository.home.GetImagesRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel
@Inject constructor(
    private val getImagesRepository: GetImagesRepository
) : BaseViewModel() {
    private val _photosUiState = MutableStateFlow(resource.loading(Status.LOADING))
    val photosUiState: StateFlow<Resource> = _photosUiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            getImagesRepository.trigger(
                viewModelScope = viewModelScope,
                replyCount = replyCount,
                params = params
            ).collectLatest {
                _photosUiState.value = it
            }
        }
    }
}