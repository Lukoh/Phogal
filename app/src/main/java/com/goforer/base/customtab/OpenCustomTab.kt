package com.goforer.base.customtab

import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.util.Patterns
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import com.goforer.base.utils.connect.ConnectionUtils.getUri

fun openCustomTab(context: Context, url: String) {
    val packageName = "com.android.chrome"

    createCustomTabsServiceConnection(context = context, packageName = packageName)
    loadContent(context, url)
}

fun createCustomTabsServiceConnection(context: Context, packageName: String) {
    var client: CustomTabsClient?
    val customTabsServiceConnection = object : CustomTabsServiceConnection() {
        override fun onCustomTabsServiceConnected(
            componentName: ComponentName,
            customTabsClient: CustomTabsClient
        ) {
            client = customTabsClient
            client?.warmup(0L)
            client?.newSession(null)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            client = null
        }
    }

    CustomTabsClient.bindCustomTabsService(context, packageName, customTabsServiceConnection)
}

fun loadContent(context: Context, url: String, color: Int = Color.TRANSPARENT) {
    if (Patterns.WEB_URL.matcher(url).matches()) {
        val params = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(color)
            .build()

        val colorScheme = CustomTabsIntent.COLOR_SCHEME_LIGHT
        val customTabsIntent = CustomTabsIntent.Builder()
            .setColorSchemeParams(colorScheme, params)
            .setShowTitle(false)
            .setUrlBarHidingEnabled(true)
            .build()

        customTabsIntent.launchUrl(context, getUri(url))
    }
}