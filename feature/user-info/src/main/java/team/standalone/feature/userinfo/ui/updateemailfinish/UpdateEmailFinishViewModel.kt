package team.standalone.feature.userinfo.ui.updateemailfinish

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.usecase.SendEmailVerificationUseCase
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.feature.userinfo.ui.updateemailfinish.UpdateEmailFinishUiEvent.NavigateToOther
import javax.inject.Inject

@HiltViewModel
class UpdateEmailFinishViewModel @Inject constructor(
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase
) : BaseViewModel<UpdateEmailFinishUiEvent>(), UpdateEmailFinishUiEventListener {

    private val _sendEmailVerificationResult = Channel<LoadResult<Unit>>()
    val sendEmailVerificationResult = _sendEmailVerificationResult.receiveAsFlow()

    override fun sendConfirmationVerification() {
        runUseCase {
            _sendEmailVerificationResult.trySend(LoadResult.Loading())
            val result = sendEmailVerificationUseCase()
            _sendEmailVerificationResult.trySend(result.asLoadResult())
        }
    }

    override fun backToOther() = sendUiEvent(NavigateToOther)
}

interface UpdateEmailFinishUiEventListener {
    fun sendConfirmationVerification()
    fun backToOther()
}

sealed class UpdateEmailFinishUiEvent {
    object NavigateToOther : UpdateEmailFinishUiEvent()
}