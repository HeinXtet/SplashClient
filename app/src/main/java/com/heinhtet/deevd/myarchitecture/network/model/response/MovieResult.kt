package com.heinhtet.deevd.myarchitecture.network.model.response

import com.squareup.moshi.Json

/**
 * Created by Hein Htet on 8/22/18.
 */

data class MovieResult(
        @Json(name = "results") var movie: List<Movie>
)

data class Movie(
        @Json(name = "title") var title: String,
        @Json(name = "poster_path") var posterPath: String,
        @Json(name = "id") var id : Int
)