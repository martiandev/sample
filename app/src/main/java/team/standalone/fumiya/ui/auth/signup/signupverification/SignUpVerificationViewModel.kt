package team.standalone.fumiya.ui.auth.signup.signupverification

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.qualifier.Inquiry
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.usecase.SendEmailVerificationUseCase
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.fumiya.ui.auth.signup.signupverification.SignUpVerificationUiEvent.NavigateToBrowser
import team.standalone.fumiya.ui.auth.signup.signupverification.SignUpVerificationUiEvent.NavigateToLogin
import javax.inject.Inject

@HiltViewModel
class SignUpVerificationViewModel @Inject constructor(
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    @Inquiry private val inquiry: String,
) : BaseViewModel<SignUpVerificationUiEvent>(), SignUpVerificationUiEventListener {
    private val _verificationResult = Channel<LoadResult<Unit>>()
    val verificationResult = _verificationResult.receiveAsFlow()

    override fun resendVerification() {
        runUseCase {
            _verificationResult.trySend(LoadResult.Loading())
            val result = sendEmailVerificationUseCase()
            _verificationResult.trySend(result.asLoadResult())
        }
    }

    override fun goToLogin() = sendUiEvent(NavigateToLogin)

    override fun openBrowser() = sendUiEvent(NavigateToBrowser(inquiry))
}

interface SignUpVerificationUiEventListener {
    fun goToLogin()
    fun resendVerification()
    fun openBrowser()
}

sealed class SignUpVerificationUiEvent {
    object NavigateToLogin : SignUpVerificationUiEvent()
    data class NavigateToBrowser(val url: String) : SignUpVerificationUiEvent()
}