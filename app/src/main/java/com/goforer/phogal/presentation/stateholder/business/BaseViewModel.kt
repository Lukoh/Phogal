package com.goforer.phogal.presentation.stateholder.business

import androidx.lifecycle.ViewModel
import com.goforer.phogal.data.network.api.Params
import timber.log.Timber
import javax.inject.Singleton

@Singleton
abstract class BaseViewModel : ViewModel() {
    open fun trigger(replyCount: Int, params: Params) {
        Timber.d("Triggered Params")
    }
}