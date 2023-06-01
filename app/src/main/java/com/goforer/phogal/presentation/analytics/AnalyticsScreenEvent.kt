package com.goforer.phogal.presentation.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.goforer.base.analytics.AnalyticsEvent
import com.goforer.base.analytics.AnalyticsHelper
import com.goforer.base.analytics.LocalAnalyticsHelper

fun AnalyticsHelper.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.SCREEN_VIEW,
            extras = listOf(
                AnalyticsEvent.Param(AnalyticsEvent.ParamKeys.SCREEN_NAME, screenName),
            ),
        ),
    )
}

@Composable
fun TrackScreenViewEvent(
    screenName: String,
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current,
) = DisposableEffect(Unit) {
    analyticsHelper.logScreenView(screenName)
    onDispose {}
}