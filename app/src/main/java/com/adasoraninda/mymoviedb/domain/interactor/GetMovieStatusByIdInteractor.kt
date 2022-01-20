package com.adasoraninda.mymoviedb.domain.interactor

import kotlinx.coroutines.flow.Flow

interface GetMovieStatusByIdInteractor {

    operator fun invoke(id: Int): Flow<Boolean>

}