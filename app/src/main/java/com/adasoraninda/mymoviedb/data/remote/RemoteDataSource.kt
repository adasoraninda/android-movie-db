package com.adasoraninda.mymoviedb.data.remote

import com.adasoraninda.mymoviedb.common.ServerException
import com.adasoraninda.mymoviedb.data.remote.api.MovieService
import com.adasoraninda.mymoviedb.data.remote.response.MovieDetailResponse
import com.adasoraninda.mymoviedb.data.remote.response.MovieResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: MovieService
) {

    suspend fun searchMovies(keyword: String): List<MovieResponse> {
        val response = service.searchMovies(keyword)

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

    suspend fun getTopRatedMovies(): List<MovieResponse> {
        val response = service.getTopRatedMovies()

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

    suspend fun getPopularMovies(): List<MovieResponse> {
        val response = service.getPopularMovies()

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

    suspend fun getNowPlayingMovies(): List<MovieResponse> {
        val response = service.getNowPlayingMovies()

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

    suspend fun getMovieById(id: Int): MovieDetailResponse {
        val response = service.getMovieById(id)
        val body = response.body()

        if (response.code() != 200 || body == null) {
            throw ServerException()
        }

        return body
    }

    suspend fun getMovieRecommendationById(id: Int): List<MovieResponse> {
        val response = service.getMovieRecommendationById(id)

        if (response.code() != 200) {
            throw ServerException()
        }

        return response.body()?.results ?: emptyList()
    }

}