package team.standalone.core.common.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import team.standalone.core.common.extension.empty

class FormItemAsFlow<Input, Output>(default: Input? = null) {
    val input = MutableStateFlow(default)
    val error = MutableStateFlow(String.empty())
    val hasError = MutableStateFlow(false)

    fun validate(
        validatorBlock: (Input?) -> Boolean,
        outputBlock: (Input?) -> Output?,
        errorBlock: (Input?) -> String? = { null },
        optional: Boolean = false,
    ): Output? {
        val newInput = this.input.value

        fun success(): Output? {
            this.hasError.update { false }
            this.error.update { String.empty() }
            return outputBlock(newInput)
        }

        fun failed(): Output? {
            this.hasError.update { true }
            this.error.update { errorBlock(newInput).orEmpty() }
            return null
        }

        return when (optional) {
            true -> {
                if (newInput is String) {
                    if (newInput.isNotEmpty()) {
                        if (validatorBlock(newInput)) {
                            success()
                        } else {
                            failed()
                        }
                    } else {
                        success()
                    }
                } else {
                    if (newInput != null) {
                        if (validatorBlock(newInput)) {
                            success()
                        } else {
                            failed()
                        }
                    } else {
                        success()
                    }
                }
            }
            false -> {
                if (validatorBlock(newInput)) {
                    success()
                } else {
                    failed()
                }
            }
        }
    }
}