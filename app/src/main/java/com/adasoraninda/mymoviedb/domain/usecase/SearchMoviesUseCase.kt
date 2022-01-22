package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.SearchMoviesInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) : SearchMoviesInteractor {

    override suspend fun invoke(keyword: String): Flow<Result<List<Movie>>> {
        return repository.searchMovies(keyword).flowOn(Dispatchers.IO)
    }
}