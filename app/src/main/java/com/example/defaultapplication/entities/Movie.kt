package com.example.defaultapplication.entities

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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