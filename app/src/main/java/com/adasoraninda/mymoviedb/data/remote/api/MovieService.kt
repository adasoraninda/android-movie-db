package com.adasoraninda.mymoviedb.data.remote.api

import com.adasoraninda.mymoviedb.data.remote.response.MovieDetailResponse
import com.adasoraninda.mymoviedb.data.remote.response.MoviesResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("/search/movie")
    suspend fun searchMovies(
        @Query("query") keyword: String,
    ): Response<MoviesResponseWrapper>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MoviesResponseWrapper>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MoviesResponseWrapper>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<MoviesResponseWrapper>

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int
    ): Response<MovieDetailResponse>

    @GET("movie/{id}/recommendations")
    suspend fun getMovieRecommendationById(
        @Path("id") id: Int
    ): Response<MoviesResponseWrapper>

}