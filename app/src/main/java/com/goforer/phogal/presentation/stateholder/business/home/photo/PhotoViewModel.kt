package com.goforer.phogal.presentation.stateholder.business.home.photo

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.response.Status
import com.goforer.phogal.data.repository.home.GetImagesRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
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
                viewModelScope = viewModelScope,
                replyCount = replyCount,
                params = params
            ).collectLatest {
                if (it.status == Status.SUCCESS) {
                    _photosUiState.value = it.data!!
                    _isRefreshing.value = false
                } else{
                    Timber.d("Not Success")
                }
            }
        }
    }
}