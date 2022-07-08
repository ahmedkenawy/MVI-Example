package com.a7medkenawy.mvi

sealed class MainViewState {
    object Idle : MainViewState()
    data class Success(val number: Int) : MainViewState()
    data class Error(val message: String) : MainViewState()
}
