package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.GetWatchlistMovieInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchlistMovieUseCase@Inject constructor(
    private val repository: MovieRepository
):GetWatchlistMovieInteractor  {

    override suspend fun invoke(): Flow<List<Movie>> {
        return repository.getAll()
    }
}