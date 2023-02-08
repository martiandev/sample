package team.standalone.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiEvent> : ViewModel() {
    protected val whileSubscribed = SharingStarted.WhileSubscribed(5_000)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun sendUiEvent(uiEvent: UiEvent) {
        _uiEvent.trySend(uiEvent)
    }

    protected fun runUseCase(task: suspend () -> Unit) {
        viewModelScope.launch { task() }
    }
}