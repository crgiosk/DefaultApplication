package com.example.defaultapplication.services

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ServerResponse<T>(
        @Json(name = "status")
        @field:Json(name = "status")
        var status: Boolean,

        @Json(name = "data")
        @field:Json(name = "data")
        var data: T?,

        @Json(name = "error")
        @field:Json(name = "error")
        var error: Error?
)
