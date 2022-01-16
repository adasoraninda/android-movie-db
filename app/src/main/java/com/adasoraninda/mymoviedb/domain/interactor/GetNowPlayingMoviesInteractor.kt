package com.adasoraninda.mymoviedb.domain.interactor

import com.adasoraninda.mymoviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetNowPlayingMoviesInteractor {

    suspend operator fun invoke(): Flow<Result<List<Movie>>>

}