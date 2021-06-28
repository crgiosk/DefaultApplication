package com.example.defaultapplication.entities

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
data class ContactResponse(
        @Json(name = "id")
        @field:Json(name = "id")
        var id: String = String(),

        @Json(name = "first_name")
        @field:Json(name = "first_name")
        var firstName: String = String(),

        @Json(name = "last_name")
        @field:Json(name = "last_name")
        val lastName: String = String(),

        @Json(name = "name")
        @field:Json(name = "name")
        val name: String = String(),

        @Json(name = "user_name")
        @field:Json(name = "user_name")
        val userName: String = String(),

        @Json(name = "email")
        @field:Json(name = "email")
        val email: String = String(),

        @Json(name = "picture")
        @field:Json(name = "picture")
        val picture: String = String(),

        @Json(name = "is_connected")
        @field:Json(name = "is_connected")
        var isConnected: String = String()

) : Serializable {

}