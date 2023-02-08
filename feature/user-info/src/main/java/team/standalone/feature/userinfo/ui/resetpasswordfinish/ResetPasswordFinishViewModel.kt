package team.standalone.feature.userinfo.ui.resetpasswordfinish

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.feature.userinfo.ui.resetpasswordfinish.ResetPasswordFinishUiEvent.NavigateToSignIn
import javax.inject.Inject

@HiltViewModel
class ResetPasswordFinishViewModel @Inject constructor(
) : BaseViewModel<ResetPasswordFinishUiEvent>(), ResetPasswordFinishUiEventListener {

    override fun backToSignIn() = sendUiEvent(NavigateToSignIn)
}

interface ResetPasswordFinishUiEventListener {
    fun backToSignIn()
}

sealed class ResetPasswordFinishUiEvent {
    object NavigateToSignIn : ResetPasswordFinishUiEvent()
}
