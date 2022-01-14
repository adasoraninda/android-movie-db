package com.adasoraninda.mymoviedb.data.local

import com.adasoraninda.mymoviedb.data.local.dao.MovieDao
import com.adasoraninda.mymoviedb.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: MovieDao
) {

    fun getAll(): Flow<List<MovieEntity>> {
        return dao.getAll()
    }

    fun isFavorite(id: Int): Flow<Boolean> {
        return dao.isFavorite(id)
    }

    suspend fun save(movieEntity: MovieEntity): Long {
        return dao.save(movieEntity)
    }

    suspend fun delete(movieEntity: MovieEntity): Int {
        return dao.delete(movieEntity)
    }

}