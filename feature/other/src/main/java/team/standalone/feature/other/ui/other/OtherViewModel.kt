package team.standalone.feature.other.ui.other

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import team.standalone.core.common.qualifier.FAQ
import team.standalone.core.common.qualifier.TransactionLaw
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.data.domain.usecase.SignOutUseCase
import team.standalone.core.data.domain.usecase.UpdateLanguageUseCase
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.feature.other.ui.other.OtherUiEvent.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class OtherViewModel @Inject constructor(
    @TransactionLaw private val transactionLaw: String,
    @FAQ private val faq: String,
    getUserUseCase: GetUserUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<OtherUiEvent>(), OtherUiEventListener {

    val uiState = getUserUseCase(true)
        .mapLatest { result ->
            OtherUiState(result)
        }.stateIn(
            scope = viewModelScope,
            started = whileSubscribed,
            initialValue = OtherUiState(LoadResult.Loading())
        )

    private val _updateLanguageResult = Channel<LoadResult<Settings>>()
    val updateLanguageResult = _updateLanguageResult.receiveAsFlow()

    private val _signOutResult = Channel<LoadResult<Unit>>()
    val signOutResult = _signOutResult.receiveAsFlow()

    override fun openUserInformation() = sendUiEvent(NavigateToUserInformation)

    override fun openChatLanguageSettings() = sendUiEvent(NavigateToChatLanguageSettings)

    override fun openPrivacyPolicy() = sendUiEvent(NavigateToPrivacyPolicy)

    override fun openTermsOfService() = sendUiEvent(NavigateToTermsOfService)

    override fun openTransactionsLaw() = sendUiEvent(OpenBrowser(transactionLaw))

    override fun openLicense() = sendUiEvent(NavigateToLicense)

    override fun openFaq() = sendUiEvent(OpenBrowser(faq))

    override fun openWithdrawAccount() = sendUiEvent(NavigateToWithdrawAccount)

    override fun openManageSubscription() = sendUiEvent(OpenManageSubscription)

    override fun openDeleteAccountWarning() = sendUiEvent(NavigateToDeleteAccountWarning)

    override fun openSignOutConfirmation() = sendUiEvent(NavigateToSignOutConfirmation)

    override fun updateLanguage(language: Settings.Language) {
        runUseCase {
            _updateLanguageResult.send(LoadResult.Loading())
            val result = updateLanguageUseCase(language)
            _updateLanguageResult.send(result.asLoadResult())
        }
    }

    override fun signOut() {
        runUseCase {
            _signOutResult.send(LoadResult.Loading())
            val result = signOutUseCase()
            _signOutResult.send(result.asLoadResult())
        }
    }
}

interface OtherUiEventListener {
    fun openUserInformation()
    fun openChatLanguageSettings()
    fun openPrivacyPolicy()
    fun openTermsOfService()
    fun openTransactionsLaw()
    fun openLicense()
    fun openFaq()
    fun openWithdrawAccount()
    fun openManageSubscription()
    fun openDeleteAccountWarning()
    fun openSignOutConfirmation()
    fun updateLanguage(language: Settings.Language)
    fun signOut()
}

sealed class OtherUiEvent {
    object NavigateToUserInformation : OtherUiEvent()
    object NavigateToChatLanguageSettings : OtherUiEvent()
    object NavigateToLicense : OtherUiEvent()
    object NavigateToWithdrawAccount : OtherUiEvent()
    object NavigateToDeleteAccountWarning : OtherUiEvent()
    object NavigateToSignOutConfirmation : OtherUiEvent()
    object NavigateToTermsOfService : OtherUiEvent()
    object NavigateToPrivacyPolicy : OtherUiEvent()
    object OpenManageSubscription : OtherUiEvent()
    data class OpenBrowser(val url: String) : OtherUiEvent()
}

data class OtherUiState(
    val userState: LoadResult<User>
)