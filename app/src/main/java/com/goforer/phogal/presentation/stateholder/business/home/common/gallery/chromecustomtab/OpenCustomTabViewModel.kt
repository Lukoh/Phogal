package com.goforer.phogal.presentation.stateholder.business.home.common.gallery.chromecustomtab

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import com.goforer.phogal.presentation.ui.openCustomTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenCustomTabViewModel
@Inject
constructor() : BaseViewModel<String>() {
    fun runCustomTab(context: Context, url: String) {
        viewModelScope.launch {
            openCustomTab(context, url)
        }
    }
}