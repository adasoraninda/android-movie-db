package com.adasoraninda.mymoviedb.domain.interactor

import com.adasoraninda.mymoviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetWatchlistMovieInteractor {

    suspend operator fun invoke(): Flow<List<Movie>>

}