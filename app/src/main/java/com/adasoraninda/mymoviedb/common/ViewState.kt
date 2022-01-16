package com.adasoraninda.mymoviedb.common

import androidx.annotation.StringRes

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Error<T>(@StringRes val message: Int) : ViewState<T>()
    data class Success<T>(val data: T) : ViewState<T>()
}