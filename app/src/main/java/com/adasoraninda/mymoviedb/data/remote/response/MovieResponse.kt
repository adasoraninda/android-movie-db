package com.adasoraninda.mymoviedb.data.remote.response

import com.adasoraninda.mymoviedb.domain.model.Movie
import com.squareup.moshi.Json

data class MovieResponse(
    @field:Json(name = "id") val id: Int = -1,
    @field:Json(name = "title") val title: String? = null,
    @field:Json(name = "poster_path") val posterPath: String? = null,
    @field:Json(name = "overview") val overview: String? = null
)

data class MoviesResponseWrapper(
    @field:Json(name = "page") val page: Int = 0,
    @field:Json(name = "results") val results: List<MovieResponse>? = null,
)

fun MovieResponse.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        overview = this.overview.orEmpty(),
    )
}