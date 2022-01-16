package com.adasoraninda.mymoviedb.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
)

fun MovieDetail.toMovieDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}