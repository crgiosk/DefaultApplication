package com.example.defaultapplication.services

sealed class UIState {
    object OnLoading: UIState()
    class OnSuccess<Data>(val data: Data): UIState()
    class OnError(val error: String): UIState()
}