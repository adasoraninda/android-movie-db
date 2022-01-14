package com.adasoraninda.mymoviedb.data.remote

import com.adasoraninda.mymoviedb.common.ServerException
import com.adasoraninda.mymoviedb.data.remote.api.MovieService
import com.adasoraninda.mymoviedb.data.remote.response.MovieDetailResponse
import com.adasoraninda.mymoviedb.data.remote.response.MoviesResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: MovieService
) {

    suspend fun searchMovies(keyword: String?): List<MoviesResponse> {
        val response = service.searchMovies(keyword)

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

    suspend fun getTopRatedMovies(): List<MoviesResponse> {
        val response = service.getTopRatedMovies()

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

    suspend fun getPopularMovies(): List<MoviesResponse> {
        val response = service.getPopularMovies()

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

    suspend fun getNowPlayingMovies(): List<MoviesResponse> {
        val response = service.getNowPlayingMovies()

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

    suspend fun getMovieById(id: Int): MovieDetailResponse? {
        val response = service.getMovieById(id)

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()
    }

    suspend fun getMovieRecommendationById(id: Int): List<MoviesResponse> {
        val response = service.getMovieRecommendationById(id)

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

}