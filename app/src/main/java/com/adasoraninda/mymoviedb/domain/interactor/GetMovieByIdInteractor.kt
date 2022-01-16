package com.adasoraninda.mymoviedb.domain.interactor

import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface GetMovieByIdInteractor {

    suspend operator fun invoke(id: Int): Flow<Result<MovieDetail>>

}