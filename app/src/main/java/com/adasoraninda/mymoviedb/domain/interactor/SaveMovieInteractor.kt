package com.adasoraninda.mymoviedb.domain.interactor

import com.adasoraninda.mymoviedb.domain.model.Movie

interface SaveMovieInteractor {

    suspend operator fun invoke(movie: Movie): Long

}