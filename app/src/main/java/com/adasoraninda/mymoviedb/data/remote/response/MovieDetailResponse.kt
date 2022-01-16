package com.adasoraninda.mymoviedb.data.remote.response

import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import com.squareup.moshi.Json

data class MovieDetailResponse(
    @field:Json(name = "id") val id: Int = -1,
    @field:Json(name = "title") val title: String? = null,
    @field:Json(name = "poster_path") val posterPath: String? = null,
    @field:Json(name = "overview") val overview: String? = null
)

fun MovieDetailResponse.toDomain(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty()
    )
}