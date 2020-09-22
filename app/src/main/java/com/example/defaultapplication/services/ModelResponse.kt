package com.example.defaultapplication.services

sealed class ModelResponse {
    class OnSuccess<Data>(val result: Data) : ModelResponse()
    class OnError(val error: String) : ModelResponse()
}
