package com.adasoraninda.mymoviedb.domain.usecase

import com.adasoraninda.mymoviedb.domain.interactor.DeleteMovieInteractor
import com.adasoraninda.mymoviedb.domain.model.Movie

class DeleteMovie : DeleteMovieInteractor {

    override suspend fun invoke(movie: Movie): Int {
        return 0
    }
}