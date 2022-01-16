package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.GetTopRatedMoviesInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase@Inject constructor(
    private val repository: MovieRepository
) :GetTopRatedMoviesInteractor {

    override suspend fun invoke(): Flow<Result<List<Movie>>> {
        return repository.getTopRatedMovies()
    }
}