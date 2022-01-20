package com.adasoraninda.mymoviedb.data.remote.response

import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import com.squareup.moshi.Json

data class MovieDetailResponse(
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "title") val title: String? = null,
    @field:Json(name = "poster_path") val posterPath: String? = null,
    @field:Json(name = "overview") val overview: String? = null,
    @field:Json(name = "genres") val genres: List<MovieGenre?>? = null,
    @field:Json(name = "vote_average") val voteAverage: Float? = null,
    @field:Json(name = "runtime") val runtime: Int? = null,
)

data class MovieGenre(
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "name") val name: String? = null
)

fun MovieDetailResponse.toDomain(): MovieDetail {
    return MovieDetail(
        id = id ?: -1,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        runtime = runtime ?: 0,
        voteAverage = voteAverage ?: 0.0F,
        genres = genres?.map { genre ->
            genre?.name
        }?.joinToString().orEmpty()
    )
}