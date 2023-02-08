package team.standalone.fumiya.ui.auth.signup.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.util.FormItem
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.param.SignUpParam
import team.standalone.core.data.domain.usecase.SignUpUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.fumiya.ui.auth.signup.signup.SignUpUiEvent.SignUpValidation
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<SignUpUiEvent>(), SignUpUiEventListener {
    private val _signUpResult = Channel<LoadResult<Unit>>()
    val signUpResult = _signUpResult.receiveAsFlow()

    var emailAddress = FormItem<String, String>()
    var password = FormItem<String, String>()
    var confirmPassword = FormItem<String, String>()

    var emailAddressInput: String? = null
    var passwordInput: String? = null

    override fun validateInputs() {
        emailAddressInput = emailAddress.validate(
            validatorBlock = { it?.let(Validator::isEmailValidFormat).orFalse() },
            outputBlock = { it }
        )

        passwordInput = password.validate(
            validatorBlock = { it?.let(Validator::isPasswordValidFormat).orFalse() },
            outputBlock = { it }
        )

        val confirmPasswordInput = confirmPassword.validate(
            validatorBlock = { input ->
                if (!passwordInput.isNullOrBlank() && !input.isNullOrBlank()) {
                    Validator.isPasswordConfirmationValid(
                        password = passwordInput.toString(),
                        input = input
                    )
                } else false
            },
            outputBlock = { it }
        )

        when {
            emailAddressInput == null -> {}
            passwordInput == null -> {}
            confirmPasswordInput == null -> {}
            else -> {
                sendUiEvent(SignUpValidation)
            }
        }
    }

    override fun registerUser() {
        runUseCase {
            val signUpParam = SignUpParam(
                email = emailAddressInput.toString().trim(),
                password = passwordInput.toString().trim()
            )
            _signUpResult.trySend(LoadResult.Loading())
            val result = signUpUseCase(signUpParam)
            _signUpResult.trySend(result.asLoadResult())
        }
    }
}

interface SignUpUiEventListener {
    fun validateInputs()
    fun registerUser()
}

sealed class SignUpUiEvent {
    object SignUpValidation : SignUpUiEvent()
    object NavigateToVerification : SignUpUiEvent()
}