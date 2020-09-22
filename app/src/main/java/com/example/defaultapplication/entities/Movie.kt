package com.example.defaultapplication.entities

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class Movie(

    @Json(name = "Title")
    @field:Json(name = "Title")
    val title: String,

    @Json(name = "Year")
    @field:Json(name = "Year")
    val year: String,

    @Json(name = "imdbID")
    @field:Json(name = "imdbID")
    val imdbID: String,

    @Json(name = "Type")
    @field:Json(name = "Type")
    val type: String,

    @Json(name = "Poster")
    @field:Json(name = "Poster")
    val poster: String
)