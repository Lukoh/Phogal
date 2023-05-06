package com.goforer.phogal

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.goforer.base.utils.connect.UnsplashSizingInterceptor
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Phogal : Application(), ImageLoaderFactory {

    /**
     * Create the singleton [ImageLoader].
     * This is used by [rememberImagePainter] to load images in the app.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(UnsplashSizingInterceptor)
            }
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        
        FirebaseApp.initializeApp(this)
    }
}