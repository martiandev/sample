package team.standalone.core.common.util

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

class FormItem<Input, Output>(default: Input? = null) {
    val input: ObservableField<Input> = ObservableField(default)
    val errMsg: ObservableField<String> = ObservableField()
    val hasError: ObservableBoolean = ObservableBoolean()

    fun validate(
        validatorBlock: (Input?) -> Boolean,
        outputBlock: (Input?) -> Output?,
        errorBlock: (Input?) -> String? = {null},
        optional: Boolean = false,
    ): Output? {
        val newInput = this.input.get()

        fun success(): Output? {
            this.hasError.set(false)
            this.errMsg.set(null)
            return outputBlock(newInput)
        }

        fun failed(): Output? {
            this.hasError.set(true)
            this.errMsg.set(errorBlock(newInput))
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

    fun reset() {
        input.set(null)
        errMsg.set(null)
        hasError.set(false)
    }
}