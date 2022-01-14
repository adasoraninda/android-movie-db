package com.adasoraninda.mymoviedb.domain.interactor

import com.adasoraninda.mymoviedb.domain.model.Movie

interface DeleteMovieInteractor {

    suspend operator fun invoke(movie: Movie): Int

}