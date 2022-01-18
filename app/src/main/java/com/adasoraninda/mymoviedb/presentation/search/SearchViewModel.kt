package com.adasoraninda.mymoviedb.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.ViewState
import com.adasoraninda.mymoviedb.domain.interactor.SearchMoviesInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchMoviesInteractor
) : ViewModel() {

    private val _viewState =
        MutableLiveData<ViewState<List<Movie>>>(ViewState.Error(R.string.text_no_results))
    val viewState: LiveData<ViewState<List<Movie>>> get() = _viewState

    fun searchData(keyword: String) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading

            useCase(keyword).collect { result ->
                result.fold(
                    onSuccess = { movies ->
                        if (movies.isEmpty()) {
                            _viewState.value = ViewState.Error(R.string.text_no_results)
                        } else {
                            _viewState.value = ViewState.Success(data = movies)
                        }
                    },
                    onFailure = {
                        _viewState.value = ViewState.Error(R.string.text_error_get_data)
                    }
                )
            }
        }
    }

}