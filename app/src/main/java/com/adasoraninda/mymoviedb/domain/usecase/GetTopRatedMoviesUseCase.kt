package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.GetTopRatedMoviesInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTopRatedMoviesUseCase@Inject constructor(
    private val repository: MovieRepository
) :GetTopRatedMoviesInteractor {

    override suspend fun invoke(): Flow<Result<List<Movie>>> {
        return repository.getTopRatedMovies().flowOn(Dispatchers.IO)
    }
}