package com.adasoraninda.mymoviedb.domain.repository

import com.adasoraninda.mymoviedb.data.local.entity.MovieEntity
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun searchMovies(keyword: String):  Flow<Result<List<Movie>>>

    suspend fun getTopRatedMovies():  Flow<Result<List<Movie>>>

    suspend fun getPopularMovies():  Flow<Result<List<Movie>>>

    suspend fun getNowPlayingMovies():  Flow<Result<List<Movie>>>

    suspend fun getMovieById(id: Int):  Flow<Result<MovieDetail>>

    suspend fun getMovieRecommendationById(id: Int):  Flow<Result<List<Movie>>>

    fun getAll(): Flow<List<Movie>>

    fun isFavorite(id: Int): Flow<Boolean>

    suspend fun save(movieEntity: MovieEntity): Long

    suspend fun delete(movieEntity: MovieEntity): Int

}