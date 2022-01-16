package com.adasoraninda.mymoviedb.data.repository

import com.adasoraninda.mymoviedb.data.local.LocalDataSource
import com.adasoraninda.mymoviedb.data.local.entity.MovieEntity
import com.adasoraninda.mymoviedb.data.local.entity.toDomain
import com.adasoraninda.mymoviedb.data.remote.RemoteDataSource
import com.adasoraninda.mymoviedb.data.remote.response.MovieResponse
import com.adasoraninda.mymoviedb.data.remote.response.toDomain
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import com.adasoraninda.mymoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
) : MovieRepository {

    override suspend fun searchMovies(keyword: String): Flow<Result<List<Movie>>> {
        return flow {
            val result = kotlin.runCatching {
                remote.searchMovies(keyword)
                    .map(MovieResponse::toDomain)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTopRatedMovies(): Flow<Result<List<Movie>>> {
        return flow {
            val result = kotlin.runCatching {
                remote.getTopRatedMovies()
                    .map(MovieResponse::toDomain)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPopularMovies(): Flow<Result<List<Movie>>> {
        return flow {
            val result = kotlin.runCatching {
                remote.getPopularMovies()
                    .map(MovieResponse::toDomain)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNowPlayingMovies(): Flow<Result<List<Movie>>> {
        return flow {
            val result = kotlin.runCatching {
                remote.getNowPlayingMovies()
                    .map(MovieResponse::toDomain)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMovieById(id: Int): Flow<Result<MovieDetail>> {
        return flow {
            val result = kotlin.runCatching {
                remote.getMovieById(id).toDomain()
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMovieRecommendationById(id: Int): Flow<Result<List<Movie>>> {
        return flow {
            val result = kotlin.runCatching {
                remote.getMovieRecommendationById(id)
                    .map(MovieResponse::toDomain)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAll(): Flow<List<Movie>> {
        return local.getAll().map { list ->
            list.map(MovieEntity::toDomain)
        }
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return local.isFavorite(id)
    }

    override suspend fun save(movieEntity: MovieEntity): Long {
        return local.save(movieEntity)
    }

    override suspend fun delete(movieEntity: MovieEntity): Int {
        return local.delete(movieEntity)
    }
}