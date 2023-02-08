package team.standalone.feature.userinfo.ui.resignin

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.util.FormItemAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.ReSignInUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.R
import team.standalone.core.ui.viewmodel.BaseAndroidViewModel
import team.standalone.feature.userinfo.ui.resignin.ReSignInUiEvent.NavigateToForgotPassword
import javax.inject.Inject

@HiltViewModel
class ReSignInViewModel @Inject constructor(
    application: Application,
    private val reSignInUseCase: ReSignInUseCase
) : BaseAndroidViewModel<ReSignInUiEvent>(application), ReSignInUiEventListener {
    val password = FormItemAsFlow<String, String>()

    private val _signInResult = Channel<LoadResult<User>>()
    val signInResult = _signInResult.receiveAsFlow()

    override fun openForgotPassword() = sendUiEvent(NavigateToForgotPassword)

    override fun signIn() {
        val passwordInput = password.validate(
            validatorBlock = { it?.let(Validator::isPasswordValid).orFalse() },
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_re_sign_in_failed_invalid_password_empty)
                } else {
                    getString(R.string.uc_re_sign_in_failed_invalid_password)
                }
            }
        )

        when (passwordInput) {
            null -> {}
            else -> {
                runUseCase {
                    _signInResult.trySend(LoadResult.Loading())
                    val result = reSignInUseCase(passwordInput)
                    _signInResult.trySend(result.asLoadResult())
                }
            }
        }
    }
}

interface ReSignInUiEventListener {
    fun openForgotPassword()
    fun signIn()
}

sealed class ReSignInUiEvent {
    object NavigateToUpdateEmail : ReSignInUiEvent()
    object NavigateToUpdatePassword : ReSignInUiEvent()
    object NavigateToForgotPassword: ReSignInUiEvent()
}