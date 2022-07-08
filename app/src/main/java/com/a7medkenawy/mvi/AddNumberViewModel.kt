package com.a7medkenawy.mvi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddNumberViewModel(application: Application) : AndroidViewModel(application) {
    val internalChannel = Channel<MainIntent>()

    private val _statFlow = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState> get() = _statFlow

    private var number = 0

    init {
        processIntent()
    }
    fun processIntent() {
        viewModelScope.launch {
            internalChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is MainIntent.AddNumber -> reduceResult()
                }
            }
        }
    }


    fun reduceResult() {
        _statFlow.value =
            try {
                MainViewState.Success(++number)
            } catch (ex: Exception) {
                MainViewState.Error(ex.message.toString())
            }

    }
}