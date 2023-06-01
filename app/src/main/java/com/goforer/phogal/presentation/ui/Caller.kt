package com.goforer.phogal.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri

object Caller {
    internal fun openBrowser(context: Context, url: String) {
        val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        context.startActivity(urlIntent)
    }
}