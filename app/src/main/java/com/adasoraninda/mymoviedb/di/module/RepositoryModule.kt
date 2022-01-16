package com.adasoraninda.mymoviedb.di.module

import com.adasoraninda.mymoviedb.data.repository.MovieRepositoryImpl
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(repository: MovieRepositoryImpl): MovieRepository

}