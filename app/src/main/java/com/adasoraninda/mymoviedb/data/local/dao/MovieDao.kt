package com.adasoraninda.mymoviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.adasoraninda.mymoviedb.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): Flow<List<MovieEntity>>

    @Query("SELECT COUNT(*) > 0 FROM movies WHERE id = :id")
    fun isFavorite(id: Int): Flow<Boolean>

    @Insert
    suspend fun save(movieEntity: MovieEntity): Long

    @Delete
    suspend fun delete(movieEntity: MovieEntity): Int

}