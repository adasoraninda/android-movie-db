package com.adasoraninda.mymoviedb.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adasoraninda.mymoviedb.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "poster_path") val posterPath: String? = null,
    @ColumnInfo(name = "overview") val overview: String? = null
)

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title.orEmpty(),
        posterPath = posterPath.orEmpty(),
        overview = overview.orEmpty(),
    )
}