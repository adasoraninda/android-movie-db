package com.adasoraninda.mymoviedb.presentation.detail

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.ViewState
import com.adasoraninda.mymoviedb.domain.interactor.*
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import com.adasoraninda.mymoviedb.domain.model.toMovieDomain
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCaseDetail: GetMovieByIdInteractor,
    private val useCaseGetStatus: GetMovieStatusByIdInteractor,
    private val useCaseSaveWatchlist: SaveMovieInteractor,
    private val useCaseRemoveWatchlist: DeleteMovieInteractor,
    private val useCaseRecommendations: GetMovieRecommendationsByIdInteractor
) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> get() = _status

    private val _movie = MutableLiveData<ViewState<MovieDetail>>()
    val movie: LiveData<ViewState<MovieDetail>> get() = _movie

    private val _movieRecommendations = MutableLiveData<ViewState<List<Movie>>>()
    val movieRecommendations: LiveData<ViewState<List<Movie>>> get() = _movieRecommendations

    @StringRes
    var watchlistMessage: Int? = null
        private set

    fun toggleWatchlistButton() {
        viewModelScope.launch {
            val movie = (_movie.value as? ViewState.Success)?.data?.toMovieDomain() ?: return@launch
            val status = _status.value ?: false

            watchlistMessage = if (status) {
                useCaseRemoveWatchlist(movie)
                R.string.detail_alert_remove_movie
            } else {
                useCaseSaveWatchlist(movie)
                R.string.detail_alert_add_movie
            }
        }
    }

    fun fetchMovieDetail(id: Int) {
        viewModelScope.launch {
            _movie.value = ViewState.Loading
            useCaseDetail(id).collect { result ->
                result.fold(
                    onSuccess = { movie ->
                        _movie.value = ViewState.Success(data = movie)
                        getMovieStatus(id)
                        getMovieRecommendations(id)
                    },
                    onFailure = {
                        _movie.value = ViewState.Error(R.string.text_error_get_data)
                    }
                )
            }
        }
    }

    private fun getMovieStatus(id: Int) {
        viewModelScope.launch {
            useCaseGetStatus(id).collect { isFavorite ->
                _status.value = isFavorite
            }
        }
    }

    private fun getMovieRecommendations(id: Int) {
        viewModelScope.launch {
            _movieRecommendations.value = ViewState.Loading

            useCaseRecommendations(id).collect { result ->
                result.fold(
                    onSuccess = { movies ->
                        if (movies.isNotEmpty()) {
                            _movieRecommendations.value = ViewState.Success(data = movies)
                        } else {
                            _movieRecommendations.value = ViewState.Error(R.string.text_empty)
                        }
                    },
                    onFailure = {
                        _movieRecommendations.value = ViewState.Error(R.string.text_error_get_data)
                    }
                )
            }
        }
    }

}