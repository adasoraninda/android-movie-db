package com.adasoraninda.mymoviedb.presentation.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.ViewState
import com.adasoraninda.mymoviedb.domain.interactor.GetWatchlistMovieInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val useCase: GetWatchlistMovieInteractor
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<List<Movie>>>()
    val viewState: LiveData<ViewState<List<Movie>>> get() = _viewState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading

            useCase().collect { movies ->
                if (movies.isEmpty()) {
                    _viewState.value = ViewState.Error(message = R.string.text_empty_watchlist)
                } else {
                    _viewState.value = ViewState.Success(data = movies)
                }
            }
        }
    }

}