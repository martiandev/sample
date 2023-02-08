package team.standalone.feature.resetpassword

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.util.FormItem
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.usecase.ResetPasswordUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseViewModel<ResetPasswordUiEvent>(), ResetPasswordUiEventListener {
    val emailAddress = FormItem<String, String>()

    private val _resetPasswordResult = Channel<LoadResult<Unit>>()
    val resetPasswordResult = _resetPasswordResult.receiveAsFlow()

    override fun send() {
        val emailAddressInput = emailAddress.validate(
            validatorBlock = { it?.let(Validator::isEmailValidFormat).orFalse() },
            outputBlock = { it }
        )
        when (emailAddressInput) {
            null -> {}
            else -> {
                runUseCase {
                    _resetPasswordResult.trySend(LoadResult.Loading())
                    val result = resetPasswordUseCase(emailAddress.input.get().toString())
                    _resetPasswordResult.trySend(result.asLoadResult())
                }
            }
        }
    }
}

interface ResetPasswordUiEventListener {
    fun send()
}

sealed class ResetPasswordUiEvent {
    object NavigateToVerification : ResetPasswordUiEvent()
}