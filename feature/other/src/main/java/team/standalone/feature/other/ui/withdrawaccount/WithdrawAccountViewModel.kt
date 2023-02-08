package team.standalone.feature.other.ui.withdrawaccount

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.util.FormItemAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.usecase.DeleteAccountUseCase
import team.standalone.core.data.domain.usecase.GetUserEmailUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.R
import team.standalone.core.ui.viewmodel.BaseAndroidViewModel
import team.standalone.feature.other.ui.withdrawaccount.WithdrawAccountUiEvent.NavigateToDeleteAccountConfirmation
import team.standalone.feature.other.ui.withdrawaccount.WithdrawAccountUiEvent.NavigateToForgotPassword
import javax.inject.Inject

@HiltViewModel
class WithdrawAccountViewModel @Inject constructor(
    application: Application,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
) : BaseAndroidViewModel<WithdrawAccountUiEvent>(application), WithdrawAccountUiEventListener {
    val email = FormItemAsFlow<String, String>()
    val password = FormItemAsFlow<String, String>()
    val enableWithdraw = combine(
        email.input,
        password.input
    ) { email, password ->
        !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    private val _deleteAccountResult = Channel<LoadResult<Unit>>()
    val deleteAccountResult = _deleteAccountResult.receiveAsFlow()

    override fun openForgotPassword() = sendUiEvent(NavigateToForgotPassword)

    override fun openDeleteAccountConfirmation() = sendUiEvent(NavigateToDeleteAccountConfirmation)

    override fun deleteAccount() {
        val emailInput = email.validate(
            validatorBlock = { it?.let(Validator::isEmailValid).orFalse() },
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_delete_account_failed_invalid_email_empty)
                } else {
                    val registeredEmail = when (val res = getUserEmailUseCase()) {
                        is Result.Success -> res.data
                        is Result.Error -> null
                    }
                    if (registeredEmail != null && registeredEmail != it) {
                        getString(R.string.uc_delete_account_failed_invalid_email_does_not_match)
                    } else {
                        getString(R.string.uc_delete_account_failed_invalid_email)
                    }
                }
            }
        )
        val passwordInput = password.validate(
            validatorBlock = { it?.let(Validator::isPasswordValid).orFalse() },
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_delete_account_failed_invalid_password_empty)
                } else {
                    getString(R.string.uc_delete_account_failed_invalid_password)
                }
            }
        )

        when {
            emailInput == null -> Unit
            passwordInput == null -> Unit
            else -> {
                runUseCase {
                    _deleteAccountResult.trySend(LoadResult.Loading())
                    val result = deleteAccountUseCase(
                        email = emailInput,
                        password = passwordInput
                    )
                    _deleteAccountResult.trySend(result.asLoadResult())
                }
            }
        }
    }
}

interface WithdrawAccountUiEventListener {
    fun openForgotPassword()
    fun openDeleteAccountConfirmation()
    fun deleteAccount()
}

sealed class WithdrawAccountUiEvent {
    object NavigateToForgotPassword : WithdrawAccountUiEvent()
    object NavigateToDeleteAccountConfirmation : WithdrawAccountUiEvent()
}