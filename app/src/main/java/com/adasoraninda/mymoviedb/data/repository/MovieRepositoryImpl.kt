package com.adasoraninda.mymoviedb.data.repository

import com.adasoraninda.mymoviedb.data.local.LocalDataSource
import com.adasoraninda.mymoviedb.data.remote.RemoteDataSource
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
) : MovieRepository {


}