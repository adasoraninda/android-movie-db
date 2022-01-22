package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.GetMovieStatusByIdInteractor
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieStatusByIdUseCase @Inject constructor(
    private val repository: MovieRepository
) : GetMovieStatusByIdInteractor {

    override fun invoke(id: Int): Flow<Boolean> {
        return repository.isFavorite(id).flowOn(Dispatchers.IO)
    }
}