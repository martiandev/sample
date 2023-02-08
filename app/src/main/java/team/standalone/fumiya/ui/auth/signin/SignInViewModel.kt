package team.standalone.fumiya.ui.auth.signin

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.util.FormItem
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.SignInParam
import team.standalone.core.data.domain.usecase.SendEmailVerificationUseCase
import team.standalone.core.data.domain.usecase.SignInUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.viewmodel.BaseAndroidViewModel
import team.standalone.fumiya.R
import team.standalone.fumiya.ui.auth.signin.LoginUiEvent.NavigateToForgotPassword
import team.standalone.fumiya.ui.auth.signin.LoginUiEvent.NavigateToRegister
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    application: Application,
    private val signInUseCase: SignInUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase
) : BaseAndroidViewModel<LoginUiEvent>(application), LoginUiEventListener {
    private val _signInResult = Channel<LoadResult<User>>()
    val signInResult = _signInResult.receiveAsFlow()

    private val _verificationResult = Channel<LoadResult<Unit>>()
    val verificationResult = _verificationResult.receiveAsFlow()

    var emailAddress = FormItem<String, String>()
    var password = FormItem<String, String>()

    override fun login() {
        val emailAddressInput = emailAddress.validate(
            validatorBlock = { it?.let(Validator::isEmailValid).orFalse() },
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.fragment_sign_in_validation_invalid_empty_email)
                } else {
                    getString(R.string.fragment_sign_in_validation_invalid_format_email)
                }
            }
        )

        val passwordInput = password.validate(
            validatorBlock = { it?.let(Validator::isPasswordValidFormat).orFalse() },
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.fragment_sign_in_validation_invalid_empty_password)
                } else {
                    getString(R.string.fragment_sign_in_validation_invalid_format_password)
                }
            }
        )

        when {
            emailAddressInput == null -> {}
            passwordInput == null -> {}
            else -> {
                runUseCase {
                    val signInParam = SignInParam(
                        email = emailAddressInput.trim(),
                        password = passwordInput.trim()
                    )
                    _signInResult.trySend(LoadResult.Loading())
                    val result = signInUseCase(signInParam)
                    _signInResult.trySend(result.asLoadResult())
                }
            }
        }
    }

    override fun register() {
        sendUiEvent(NavigateToRegister)
    }

    override fun forgotPassword() {
        sendUiEvent(NavigateToForgotPassword)
    }

    fun resendVerification() {
        runUseCase {
            _verificationResult.trySend(LoadResult.Loading())
            val result = sendEmailVerificationUseCase()
            _verificationResult.trySend(result.asLoadResult())
        }
    }
}

interface LoginUiEventListener {
    fun login()
    fun register()
    fun forgotPassword()
}

sealed class LoginUiEvent {
    object NavigateToDashboard : LoginUiEvent()
    object NavigateToInputDetails : LoginUiEvent()
    object NavigateToRegister : LoginUiEvent()
    object NavigateToForgotPassword : LoginUiEvent()
}
