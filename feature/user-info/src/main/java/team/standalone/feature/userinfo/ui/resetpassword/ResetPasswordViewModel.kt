package team.standalone.feature.userinfo.ui.resetpassword

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.util.FormItemAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.GetUserEmailUseCase
import team.standalone.core.data.domain.usecase.ResetPasswordUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.R
import team.standalone.core.ui.viewmodel.BaseAndroidViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    application: Application,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseAndroidViewModel<ResetPasswordUiEvent>(application), ResetPasswordUiEventListener {
    val email = FormItemAsFlow<String, String>()
    val enableReset = email.input.mapLatest { email ->
        !email.isNullOrEmpty()
    }

    private val _resetPasswordResult = Channel<LoadResult<Unit>>()
    val resetPasswordResult = _resetPasswordResult.receiveAsFlow()

    override fun resetPassword() {
        val emailInput = email.validate(
            validatorBlock = { input ->
                if (input != null) {
                    if (Validator.isEmailValid(input)) {
                        val registeredEmail = when (val res = getUserEmailUseCase()) {
                            is Result.Success -> res.data
                            is Result.Error -> null
                        }
                        !(registeredEmail != null && registeredEmail != input)
                    } else {
                        false
                    }
                } else {
                    false
                }
            },
            outputBlock = { it },
            errorBlock = { input ->
                if (input != null) {
                    if (Validator.isEmailValid(input)) {
                        val registeredEmail = when (val res = getUserEmailUseCase()) {
                            is Result.Success -> res.data
                            is Result.Error -> null
                        }
                        if (registeredEmail != null && registeredEmail != input) {
                            getString(R.string.uc_reset_password_failed_invalid_email_does_not_match)
                        } else {
                            getString(R.string.uc_reset_password_failed_invalid_email)
                        }
                    } else {
                        getString(R.string.uc_reset_password_failed_invalid_email)
                    }
                } else {
                    getString(R.string.uc_reset_password_failed_invalid_email)
                }
            }
        )

        when (emailInput) {
            null -> Unit
            else -> {
                runUseCase {
                    _resetPasswordResult.trySend(LoadResult.Loading())
                    val result = resetPasswordUseCase()
                    _resetPasswordResult.trySend(result.asLoadResult())
                }
            }
        }
    }
}

interface ResetPasswordUiEventListener {
    fun resetPassword()
}

sealed class ResetPasswordUiEvent

data class ResetPasswordUiState(
    val userState: LoadResult<User>
)