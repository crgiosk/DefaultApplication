package com.example.defaultapplication.services

sealed class UIState<out T> {
    object OnLoading: UIState<Nothing>()
    class OnSuccess<Data>(val data: Data): UIState<Data>()
    class OnError(val error: String): UIState<Nothing>()
}