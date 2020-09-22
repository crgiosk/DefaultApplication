package com.example.defaultapplication.services

sealed class ConnectionResponse {
    class OnSuccess(val result: String) : ConnectionResponse()
    object OnFailure : ConnectionResponse()
    class OnResponseUnsuccessful(val code: Int) : ConnectionResponse()
    object OnInvalidData : ConnectionResponse()
}