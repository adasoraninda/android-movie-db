package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.GetMovieRecommendationsByIdInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieRecommendationsByIdUseCase @Inject constructor(
    private val repository: MovieRepository
) : GetMovieRecommendationsByIdInteractor {

    override suspend fun invoke(id: Int): Flow<Result<List<Movie>>> {
        return repository.getMovieRecommendationById(id)
    }
}