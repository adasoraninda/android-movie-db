package com.adasoraninda.mymoviedb.presentation.categories.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.ViewState
import com.adasoraninda.mymoviedb.domain.interactor.GetPopularMoviesInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val useCase: GetPopularMoviesInteractor
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<List<Movie>>>()
    val viewState: LiveData<ViewState<List<Movie>>> get() = _viewState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading

            useCase().collect { result ->
                result.fold(
                    onSuccess = { movies ->
                        _viewState.value = ViewState.Success(data = movies)
                    },
                    onFailure = {
                        _viewState.value = ViewState.Error(R.string.text_error_get_data)
                    }
                )
            }
        }
    }

}