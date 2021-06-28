package com.example.defaultapplication.services

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class Error(
        @Json(name = "code")
        @field:Json(name = "code")
        val code: String,

        @Json(name = "message")
        @field:Json(name = "message")
        val message: String,

        @Json(name = "description")
        @field:Json(name = "description")
        val description: String?
)