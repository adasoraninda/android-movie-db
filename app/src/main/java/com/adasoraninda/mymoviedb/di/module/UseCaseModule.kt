package com.adasoraninda.mymoviedb.di.module

import com.adasoraninda.mymoviedb.domain.interactor.*
import com.adasoraninda.mymoviedb.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindDeleteMovieUseCase(useCase: DeleteMovieUseCase): DeleteMovieInteractor

    @Binds
    fun bindGetMovieByIdUseCase(useCase: GetMovieByIdUseCase): GetMovieByIdInteractor

    @Binds
    fun bindGetMovieRecommendationsByIdUseCase(useCase: GetMovieRecommendationsByIdUseCase): GetMovieRecommendationsByIdInteractor

    @Binds
    fun bindGetNowPlayingMoviesUseCase(useCase: GetNowPlayingMoviesUseCase): GetNowPlayingMoviesInteractor

    @Binds
    fun bindGetPopularMoviesUseCase(useCase: GetPopularMoviesUseCase): GetPopularMoviesInteractor

    @Binds
    fun bindGetTopRatedMoviesUseCase(useCase: GetTopRatedMoviesUseCase): GetTopRatedMoviesInteractor

    @Binds
    fun bindGetWatchlistMovieUseCase(useCase: GetWatchlistMovieUseCase): GetWatchlistMovieInteractor

    @Binds
    fun bindSaveMovieUseCase(useCase: SaveMovieUseCase): SaveMovieInteractor

    @Binds
    fun bindSearchMoviesUseCase(useCase: SearchMoviesUseCase): SearchMoviesInteractor

    @Binds
    fun bindGetMovieStatusByIdUseCase(useCase: GetMovieStatusByIdUseCase): GetMovieStatusByIdInteractor
}