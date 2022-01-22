package com.adasoraninda.mymoviedb.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.ViewState
import com.adasoraninda.mymoviedb.domain.interactor.GetNowPlayingMoviesInteractor
import com.adasoraninda.mymoviedb.domain.interactor.GetPopularMoviesInteractor
import com.adasoraninda.mymoviedb.domain.interactor.GetTopRatedMoviesInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCaseNowPlaying: GetNowPlayingMoviesInteractor,
    private val useCasePopular: GetPopularMoviesInteractor,
    private val useCaseTopRated: GetTopRatedMoviesInteractor,
) : ViewModel() {

    private val _nowPlayingState = MutableLiveData<ViewState<List<Movie>>>()
    val nowPlayingState: LiveData<ViewState<List<Movie>>> get() = _nowPlayingState

    private val _popularState = MutableLiveData<ViewState<List<Movie>>>()
    val popularState: LiveData<ViewState<List<Movie>>> get() = _popularState

    private val _topRatedState = MutableLiveData<ViewState<List<Movie>>>()
    val topRatedState: LiveData<ViewState<List<Movie>>> get() = _topRatedState

    init {
        fetchData()
    }

    fun fetchData() {
        fetchTopRatedData()
        fetchPopularData()
        fetchNowPlayingData()
    }

    private fun fetchNowPlayingData() {
        viewModelScope.launch {
            _nowPlayingState.value = ViewState.Loading

            useCaseNowPlaying().collect { result ->
                result.fold(
                    onSuccess = {
                        Timber.d("now playing: $it")
                        _nowPlayingState.value = ViewState.Success(data = it)
                    },
                    onFailure = {
                        _nowPlayingState.value =
                            ViewState.Error(message = R.string.text_error_get_data)
                    }
                )
            }
        }
    }

    private fun fetchTopRatedData() {
        viewModelScope.launch {
            _topRatedState.value = ViewState.Loading

            useCaseTopRated().collect { result ->
                result.fold(
                    onSuccess = {
                        Timber.d("top rated: $it")
                        _topRatedState.value = ViewState.Success(data = it)
                    },
                    onFailure = {
                        _topRatedState.value =
                            ViewState.Error(message = R.string.text_error_get_data)
                    }
                )
            }
        }
    }

    private fun fetchPopularData() {
        viewModelScope.launch {
            _popularState.value = ViewState.Loading

            useCasePopular().collect { result ->
                result.fold(
                    onSuccess = {
                        Timber.d("popular: $it")
                        _popularState.value = ViewState.Success(data = it)
                    },
                    onFailure = {
                        _popularState.value =
                            ViewState.Error(message = R.string.text_error_get_data)
                    }
                )
            }
        }
    }

}