package com.goforer.base.customtab

import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import com.goforer.base.utils.connect.ConnectionUtils.getUri
import timber.log.Timber

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
            client?.newSession(object : CustomTabsCallback() {
                override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
                    super.onNavigationEvent(navigationEvent, extras)
                    when (navigationEvent) {
                        NAVIGATION_STARTED -> {
                            Timber.d("NAVIGATION_STARTED")
                        }
                        NAVIGATION_FINISHED -> {
                            Timber.d("NAVIGATION_FINISHED")
                        }
                        NAVIGATION_FAILED -> {
                            Timber.d("NAVIGATION_FAILED")
                        }
                        NAVIGATION_ABORTED -> {
                            Timber.d("NAVIGATION_ABORTED")
                        }
                    }
                }
            })
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
            .setStartAnimations(context, android.R.anim.slide_out_right, android.R.anim.slide_in_left)
            .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .setUrlBarHidingEnabled(true)
            .build()

        customTabsIntent.launchUrl(context, getUri(url))
    }
}