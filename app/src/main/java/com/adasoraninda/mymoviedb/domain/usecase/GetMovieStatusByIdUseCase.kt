package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.GetMovieStatusByIdInteractor
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieStatusByIdUseCase @Inject constructor(
    private val repository: MovieRepository
) : GetMovieStatusByIdInteractor {

    override fun invoke(id: Int): Flow<Boolean> {
        return repository.isFavorite(id)
    }
}