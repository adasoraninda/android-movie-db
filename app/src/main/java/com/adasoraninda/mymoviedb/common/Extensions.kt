package com.adasoraninda.mymoviedb.common

import android.content.res.Resources
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.adasoraninda.mymoviedb.R

fun ImageView.loadWithLoading(url: String?,progressBar: ProgressBar) {
    val listener = object : ImageRequest.Listener{
        override fun onStart(request: ImageRequest) {
            super.onStart(request)
            progressBar.isVisible = true
        }

        override fun onError(request: ImageRequest, throwable: Throwable) {
            super.onError(request, throwable)
            progressBar.isVisible = false
        }

        override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
            super.onSuccess(request, metadata)
            progressBar.isVisible = false
        }
    }

    load("${Constant.imageFormat}$url") {
        crossfade(true)
        error(R.drawable.ic_error_image)
        listener(listener)
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
