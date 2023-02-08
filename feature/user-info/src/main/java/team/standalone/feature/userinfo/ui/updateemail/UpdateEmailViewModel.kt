package team.standalone.feature.userinfo.ui.updateemail

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import team.standalone.core.common.util.FormItemAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.GetUserEmailUseCase
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.data.domain.usecase.UpdateEmailUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.R
import team.standalone.core.ui.viewmodel.BaseAndroidViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateEmailViewModel @Inject constructor(
    application: Application,
    getUserUseCase: GetUserUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase
) : BaseAndroidViewModel<UpdateEmailUiEvent>(application), UpdateEmailUiEventListener {
    val newEmail = FormItemAsFlow<String, String>()
    val newEmailConfirmation = FormItemAsFlow<String, String>()

    val uiState = getUserUseCase(true)
        .map { result ->
            UpdateEmailUiState(result)
        }.stateIn(
            scope = viewModelScope,
            started = whileSubscribed,
            initialValue = UpdateEmailUiState(LoadResult.Loading())
        )

    private val _updateEmailResult = Channel<LoadResult<User>>()
    val updateEmailResult = _updateEmailResult.receiveAsFlow()

    override fun updateEmail() {
        val newEmailInput = newEmail.validate(
            validatorBlock = { input ->
                if (input != null) {
                    newEmailInputValidator(input)
                } else {
                    false
                }
            },
            outputBlock = { it },
            errorBlock = { input ->
                if (input != null) {
                    newEmailInputErrorBlock(input)
                } else {
                    getString(R.string.uc_update_email_failed_invalid_new_email_empty)
                }
            }
        )

        val newEmailConfirmationInput = newEmailConfirmation.validate(
            validatorBlock = { input ->
                if (!newEmailInput.isNullOrBlank() && !input.isNullOrBlank()) {
                    Validator.isEmailConfirmationValid(
                        email = newEmailInput,
                        input = input
                    )
                } else false
            },
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_update_email_failed_invalid_new_email_empty)
                } else {
                    newEmailConfirmationInputErrorBlock(it, newEmailInput)
                }
            }
        )

        when {
            newEmailInput == null -> Unit
            newEmailConfirmationInput == null -> Unit
            else -> {
                runUseCase {
                    _updateEmailResult.trySend(LoadResult.Loading())
                    val result = updateEmailUseCase(newEmailInput)
                    _updateEmailResult.trySend(result.asLoadResult())
                }
            }
        }
    }

    /**
     * validatorBlock for the newEmailInput
     * */
    private fun newEmailInputValidator(input: String): Boolean {
        return if (Validator.isEmailValid(input)) {
            val registeredEmail = getUserCurrentEmail()
            !(registeredEmail != null && registeredEmail == input)
        } else {
            false
        }
    }

    /**
     * errorBlock for the newEmailInput
     * */
    private fun newEmailInputErrorBlock(input: String): String {
        return if (Validator.isEmailValid(input)) {
            val registeredEmail = getUserCurrentEmail()
            if (registeredEmail != null && registeredEmail == input) {
                getString(R.string.uc_update_email_failed_invalid_email)
            } else {
                getString(R.string.uc_update_email_failed_invalid_new_email)
            }
        } else {
            getString(R.string.uc_update_email_failed_invalid_new_email)
        }
    }

    /**
     * errorBlock for the newEmailConfirmationInput
     * */
    private fun newEmailConfirmationInputErrorBlock(
        emailConfirmation: String,
        newEmailInput: String?
    ): String {
        return if (Validator.isEmailValid(emailConfirmation)) {
            val registeredEmail = getUserCurrentEmail()
            if (newEmailInput != null && newEmailInput != emailConfirmation) {
                getString(R.string.uc_update_email_failed_invalid_new_email_confirmation)
            } else if (registeredEmail != null && registeredEmail == emailConfirmation) {
                getString(R.string.uc_update_email_failed_invalid_email)
            } else {
                getString(R.string.uc_update_email_failed_invalid_new_email_confirmation)
            }
        } else {
            getString(R.string.uc_update_email_failed_invalid_new_email_confirmation)
        }
    }

    /**
     * get the current user email address from [GetUserEmailUseCase]
     * */
    private fun getUserCurrentEmail(): String? {
        return when (val res = getUserEmailUseCase()) {
            is Result.Success -> res.data
            is Result.Error -> null
        }
    }
}

interface UpdateEmailUiEventListener {
    fun updateEmail()
}

sealed class UpdateEmailUiEvent {
    object NavigateToUpdateEmailFinish : UpdateEmailUiEvent()
}

data class UpdateEmailUiState(
    val userState: LoadResult<User>
)