package team.standalone.feature.userinfo.ui.updatepassword

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.util.FormItemAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.usecase.UpdatePasswordUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.R
import team.standalone.core.ui.viewmodel.BaseAndroidViewModel
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    application: Application,
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : BaseAndroidViewModel<UpdatePasswordUiEvent>(application), UpdatePasswordUiEventListener {
    val newPassword = FormItemAsFlow<String, String>()
    val newPasswordConfirmation = FormItemAsFlow<String, String>()

    val enableUpdate = combine(
        newPassword.input,
        newPasswordConfirmation.input,
    ) { newPassword, newPasswordConfirmation ->
        !newPassword.isNullOrEmpty() && !newPasswordConfirmation.isNullOrEmpty()
    }

    private val _updatePasswordResult = Channel<LoadResult<Unit>>()
    val updatePasswordResult = _updatePasswordResult.receiveAsFlow()

    override fun updatePassword() {
        val newPasswordInput = newPassword.validate(
            validatorBlock = { it?.let(Validator::isPasswordValid).orFalse() },
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_update_password_failed_invalid_new_password_empty)
                } else {
                    getString(R.string.uc_update_password_failed_invalid_new_password)
                }
            }
        )

        val newPasswordConfirmationInput = newPasswordConfirmation.validate(
            validatorBlock = { input ->
                if (!newPasswordInput.isNullOrBlank() && !input.isNullOrBlank()) {
                    Validator.isPasswordConfirmationValid(
                        password = newPasswordInput,
                        input = input
                    )
                } else false
            },
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_update_password_failed_invalid_new_password_confirmation_empty)
                } else {
                    getString(R.string.uc_update_password_failed_invalid_new_password_confirmation)
                }
            }
        )

        when {
            newPasswordInput == null -> Unit
            newPasswordConfirmationInput == null -> Unit
            else -> {
                runUseCase {
                    _updatePasswordResult.trySend(LoadResult.Loading())
                    val result = updatePasswordUseCase(newPasswordInput)
                    _updatePasswordResult.trySend(result.asLoadResult())
                }
            }
        }
    }
}

interface UpdatePasswordUiEventListener {
    fun updatePassword()
}

sealed class UpdatePasswordUiEvent