package com.adasoraninda.mymoviedb.data.remote.response

import com.squareup.moshi.Json

data class MoviesResponse(
    @Json(name = "id") val id: Int = -1,
    @Json(name = "title") val title: String? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "overview") val overview: String? = null
)

data class MoviesResponseWrapper(
    @Json(name = "page") val page: Int = 0,
    @Json(name = "results") val results: List<MoviesResponse>? = null,
)