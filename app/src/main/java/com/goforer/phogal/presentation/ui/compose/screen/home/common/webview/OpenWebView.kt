package com.goforer.phogal.presentation.ui.compose.screen.home.common.webview

import android.annotation.SuppressLint
import android.webkit.WebSettings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun OpenWebView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    url: String,
    onBackPressed: () -> Unit
) {
    val webViewState = rememberWebViewState(url = url)
    val webViewNavigator = rememberWebViewNavigator()

    WebView(
        modifier = modifier.padding(
            0.dp,
            contentPadding.calculateTopPadding(),
            0.dp,
            0.dp
        ),
        state = webViewState,
        navigator = webViewNavigator,
        onCreated = {
            with(it) {
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    javaScriptCanOpenWindowsAutomatically = false
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    builtInZoomControls = true
                    displayZoomControls = false
                    setSupportZoom(true)
                    defaultTextEncodingName = "utf-8"
                    pluginState = WebSettings.PluginState.ON
                }
            }
        }
    )

    BackHandler(enabled = true) {
        if (webViewNavigator.canGoBack) {
            webViewNavigator.navigateBack()
        } else {
            onBackPressed()
        }
    }
}