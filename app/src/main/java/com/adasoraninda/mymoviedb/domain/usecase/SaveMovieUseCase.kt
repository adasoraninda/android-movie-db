package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.SaveMovieInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.model.toEntity
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import javax.inject.Inject

class SaveMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) : SaveMovieInteractor {

    override suspend fun invoke(movie: Movie): Long {
        return repository.save(movie.toEntity())
    }
}