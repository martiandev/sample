package team.standalone.core.common.extension

import androidx.databinding.ObservableField

fun <T: Any> ObservableField<T>.initIfNull(value: T?) {
    if (get() == null) set(value)
}