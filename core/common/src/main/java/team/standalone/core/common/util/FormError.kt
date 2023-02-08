package team.standalone.core.common.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import team.standalone.core.common.extension.empty

class FormError {
    val errorMessage = MutableStateFlow(String.empty())
    val hasError = MutableStateFlow(false)

    fun setError(error: String) {
        errorMessage.update { error }
        hasError.update { true }
    }

    fun clear() {
        errorMessage.update { String.empty() }
        hasError.update { false }
    }
}