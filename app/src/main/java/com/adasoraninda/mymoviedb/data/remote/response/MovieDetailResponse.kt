package com.adasoraninda.mymoviedb.data.remote.response

import com.squareup.moshi.Json

data class MovieDetailResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "overview") val overview: String
)

fun MovieDetailResponse.toItemList(): MoviesResponse {
    return MoviesResponse(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}