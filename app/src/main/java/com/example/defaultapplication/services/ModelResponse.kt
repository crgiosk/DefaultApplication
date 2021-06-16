package com.example.defaultapplication.services

sealed class ModelResponse<out T> {
    class OnSuccess<Data>(val result: Data) : ModelResponse<Data>()
    class OnError(val error: String) : ModelResponse<Nothing>()
}
