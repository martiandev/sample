package team.standalone.core.ui.viewmodel

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseAndroidViewModel<UiEvent>(
    application: Application
) : AndroidViewModel(application) {
    protected val whileSubscribed = SharingStarted.WhileSubscribed(5_000)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    protected fun getString(@StringRes resId: Int): String {
        return getApplication<Application>().getString(resId)
    }

    fun sendUiEvent(uiEvent: UiEvent) {
        _uiEvent.trySend(uiEvent)
    }

    protected fun runUseCase(task: suspend () -> Unit) {
        viewModelScope.launch { task() }
    }
}