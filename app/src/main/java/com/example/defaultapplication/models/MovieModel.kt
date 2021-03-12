package com.example.defaultapplication.models

import com.example.defaultapplication.core.Constants
import com.example.defaultapplication.core.fromJson
import com.example.defaultapplication.core.parseJsonStringToClass
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.services.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MovieModel {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private enum class Action : Endpoint {
        GET_MOVIE_BY_TITLE {
            override val base = Constants.TEXT_DOMAIN
            override var path = ""
        }
    }

    fun getMovieByTitle(title: String, completion: (ModelResponse) -> Unit) {
        val values: MutableMap<String, Any> = mutableMapOf()
        values["apikey"] = Constants.APP_KEY
        values["t"] = title
        Action.GET_MOVIE_BY_TITLE.path = "?s=${values["t"]}&apikey=${values["apikey"]}"
        Connection.send(Action.GET_MOVIE_BY_TITLE, values) { response ->
            when (response) {
                is ConnectionResponse.OnSuccess -> {
                    completion(
                        response.result.parseJsonStringToClass<Array<Movie>>("HR001")
                    )
                }
                is ConnectionResponse.OnFailure -> {
                    val error = "PR4000 , ${Connection.ERROR_CONNECTION}, OnFailure)"
                    completion(ModelResponse.OnError(error))
                }
                is ConnectionResponse.OnResponseUnsuccessful -> {
                    if (response.code == 412) {
                        val error = "PR4002 , ${Connection.ERROR_SERVER}, OnResponseUnsuccessful 412"
                        completion(ModelResponse.OnError(error))
                    } else {
                        val error = "PR4003 , ${Connection.ERROR_SERVER}, OnResponseUnsuccessful)"
                        completion(ModelResponse.OnError(error))
                    }
                }
                is ConnectionResponse.OnInvalidData -> {
                    val error = "PR4001 , ${Connection.ERROR_SECURITY}, OnInvalidData)"
                    completion(ModelResponse.OnError(error))
                }
            }
        }

    }

    fun getMovieByTitlse(title: String, completion: (ModelResponse) -> Unit) {
        val values: MutableMap<String, Any> = mutableMapOf()
        values["apikey"] = Constants.APP_KEY
        values["t"] = title
        Action.GET_MOVIE_BY_TITLE.path = "?s=${values["t"]}&apikey=${values["apikey"]}"
        Connection.send(Action.GET_MOVIE_BY_TITLE, values) { response ->
            when (response) {
                is ConnectionResponse.OnSuccess -> {
                    completion(
                        response.result.parseJsonStringToClass<Movie>("HR002")
                    )
                }
                is ConnectionResponse.OnFailure -> {
                    val error = "PR4000 , ${Connection.ERROR_CONNECTION}, OnFailure)"
                    completion(ModelResponse.OnError(error))
                }
                is ConnectionResponse.OnResponseUnsuccessful -> {
                    if (response.code == 412) {
                        val error = "PR4002 , ${Connection.ERROR_SERVER}, OnResponseUnsuccessful 412"
                        completion(ModelResponse.OnError(error))
                    } else {
                        val error = "PR4003 , ${Connection.ERROR_SERVER}, OnResponseUnsuccessful)"
                        completion(ModelResponse.OnError(error))
                    }
                }
                is ConnectionResponse.OnInvalidData -> {
                    val error = "PR4001 , ${Connection.ERROR_SECURITY}, OnInvalidData)"
                    completion(ModelResponse.OnError(error))
                }
            }
        }

    }
}