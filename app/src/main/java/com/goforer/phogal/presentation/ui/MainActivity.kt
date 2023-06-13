package com.goforer.phogal.presentation.ui

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.goforer.base.analytics.AnalyticsHelper
import com.goforer.base.analytics.LocalAnalyticsHelper
import com.goforer.base.extension.findActivity
import com.goforer.base.utils.connect.ConnectivityManagerNetworkMonitor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.goforer.phogal.presentation.ui.compose.screen.MainScreen
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var connectivityManagerNetworkMonitor: ConnectivityManagerNetworkMonitor

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    companion object {
        internal const val SplashWaitTime = 2000L
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepOnSplash = false

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(SplashWaitTime)
                keepOnSplash = true
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            keepOnSplash
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = isSystemInDarkTheme()

            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }

            CompositionLocalProvider(LocalAnalyticsHelper provides analyticsHelper) {
                PhogalTheme(
                    darkTheme = darkTheme,
                    androidTheme = true
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize().statusBarsPadding(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen(
                            networkMonitor = connectivityManagerNetworkMonitor,
                            windowSizeClass = calculateWindowSizeClass(this)
                        )
                    }
                }
            }
        }
    }
}

fun openCustomTab(context: Context, url: String) {
    val mCustomTabsServiceConnection: CustomTabsServiceConnection?
    var mClient: CustomTabsClient?
    var mCustomTabsSession: CustomTabsSession? = null

    mCustomTabsServiceConnection = object : CustomTabsServiceConnection() {
        override fun onCustomTabsServiceConnected(componentName: ComponentName, customTabsClient: CustomTabsClient) {
            mClient = customTabsClient
            mClient?.warmup(0L)
            mCustomTabsSession = mClient?.newSession(null)
        }
        override fun onServiceDisconnected(name: ComponentName) {
            mClient = null
        }
    }

    CustomTabsClient.bindCustomTabsService(context.findActivity(), "com.android.chrome", mCustomTabsServiceConnection)

    val customTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
        .setShowTitle(true)
        .build()

    customTabsIntent.launchUrl(context.findActivity(), Uri.parse(url))
}