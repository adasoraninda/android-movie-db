package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.GetMovieByIdInteractor
import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MovieRepository
) : GetMovieByIdInteractor {

    override suspend fun invoke(id: Int): Flow<Result<MovieDetail>> {
        return repository.getMovieById(id).flowOn(Dispatchers.IO)
    }
}