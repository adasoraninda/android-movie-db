package com.adasoraninda.mymoviedb.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val genres: String,
    val voteAverage: Float,
    val runtime: Int,
)

fun MovieDetail.toMovieDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}