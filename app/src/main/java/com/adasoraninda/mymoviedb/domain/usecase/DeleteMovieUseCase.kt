package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.DeleteMovieInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.model.toEntity
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val repository: MovieRepository
)  : DeleteMovieInteractor {

    override suspend fun invoke(movie: Movie): Int {
        return repository.delete(movie.toEntity())
    }
}