package com.goforer.phogal.presentation.stateholder.business.home.gallery.photo

import androidx.lifecycle.viewModelScope
import com.goforer.phogal.data.model.remote.response.gallery.photo.Picture
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.repository.gallery.photo.GetPictureRepository
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel
@Inject constructor(
    private val getPictureRepository: GetPictureRepository
) : BaseViewModel<Picture>() {
    private val _pictureUiState = MutableStateFlow(Any())
    val pictureUiState: StateFlow<Any> = _pictureUiState

    override fun trigger(replyCount: Int, params: Params) {
        viewModelScope.launch {
            getPictureRepository.trigger(
                replyCount = replyCount,
                params = params
            ).stateIn(viewModelScope)
             .collectLatest {
                 val response = handleResponse(it)

                _pictureUiState.value = response
             }
        }
    }
}