package com.example.defaultapplication.services

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ServerResponse<Data>(
    @Json(name = "Response")
    @field:Json(name = "Response")
    var status: String?,

    @Json(name = "Search")
    @field:Json(name = "Search")
    var data: Data?,

    @Json(name = "totalResults")
    @field:Json(name = "totalResults")
    var error: String?
)
