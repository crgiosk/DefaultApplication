package com.example.defaultapplication.models

import com.example.defaultapplication.core.Constants
import com.example.defaultapplication.core.parseJsonStringToClass
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.services.ModelResponse
import com.example.defaultapplication.services.Endpoint
import com.example.defaultapplication.services.ConnectionResponse
import com.example.defaultapplication.services.Connection

class MovieModel {

    private enum class Action : Endpoint {
        GET_MOVIE_BY_TITLE {
            override val base = Constants.TEXT_DOMAIN
            override var path = ""
        }
    }

    fun getMovieByTitle(title: String, completion: (ModelResponse<Array<Movie>>) -> Unit) {
        val values: MutableMap<String, Any> = mutableMapOf()
        values["apikey"] = Constants.APP_KEY
        values["t"] = title
        Action.GET_MOVIE_BY_TITLE.path = "?s=${values["t"]}&apikey=${values["apikey"]}"
        Connection.send(Action.GET_MOVIE_BY_TITLE, values) { response ->
            when (response) {
                is ConnectionResponse.OnSuccess -> {
                    completion(
                        response.result.parseJsonStringToClass("HR001")
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

    fun getMovieByTitlse(title: String, completion: (ModelResponse<Movie>) -> Unit) {
        val values: MutableMap<String, Any> = mutableMapOf()
        values["apikey"] = Constants.APP_KEY
        values["t"] = title
        Action.GET_MOVIE_BY_TITLE.path = "?s=${values["t"]}&apikey=${values["apikey"]}"
        Connection.send(Action.GET_MOVIE_BY_TITLE, values) { response ->
            when (response) {
                is ConnectionResponse.OnSuccess -> {
                    completion(
                        response.result.parseJsonStringToClass("HR002")
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