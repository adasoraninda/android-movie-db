package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.GetPopularMoviesInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) : GetPopularMoviesInteractor {

    override suspend fun invoke(): Flow<Result<List<Movie>>> {
        return repository.getPopularMovies().flowOn(Dispatchers.IO)
    }
}