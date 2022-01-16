package com.adasoraninda.mymoviedb.domain.model

import com.adasoraninda.mymoviedb.data.local.entity.MovieEntity

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
)

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview
    )
}