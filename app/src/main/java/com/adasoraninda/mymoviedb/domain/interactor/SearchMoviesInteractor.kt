package com.adasoraninda.mymoviedb.domain.interactor

import com.adasoraninda.mymoviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface SearchMoviesInteractor {

    suspend operator fun invoke(keyword: String): Flow<Result<List<Movie>>>

}