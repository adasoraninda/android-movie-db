package com.adasoraninda.mymoviedb.domain.interactor

import com.adasoraninda.mymoviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieRecommendationsByIdInteractor {

    suspend operator fun invoke(id: Int): Flow<Result<List<Movie>>>

}