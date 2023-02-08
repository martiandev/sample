package team.standalone.core.common.extension

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T> MutableStateFlow<T>.updateIfNull(newValue: T) {
    if(this.value == null) {
        this.update { newValue }
    }
}