package team.standalone.feature.other.ui.chatlanguagesettings

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import team.standalone.core.common.util.LoadResult
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.data.domain.usecase.GetSettingsUseCase
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.feature.other.ui.chatlanguagesettings.ChatLanguageSettingsUiEvent.SelectLanguage
import javax.inject.Inject

@HiltViewModel
class ChatLanguageSettingsViewModel @Inject constructor(
    getSettingsUseCase: GetSettingsUseCase,
) : BaseViewModel<ChatLanguageSettingsUiEvent>(), ChatLanguageSettingsUiEventListener {
    val uiState = getSettingsUseCase()
        .map { result ->
            ChatLanguageSettingsUiState(result)
        }.stateIn(
            scope = viewModelScope,
            started = whileSubscribed,
            initialValue = ChatLanguageSettingsUiState(LoadResult.Loading())
        )

    override fun selectLanguage(language: Settings.Language) = sendUiEvent(SelectLanguage(language))
}

interface ChatLanguageSettingsUiEventListener {
    fun selectLanguage(language: Settings.Language)
}

sealed class ChatLanguageSettingsUiEvent {
    data class SelectLanguage(val language: Settings.Language) : ChatLanguageSettingsUiEvent()
}

data class ChatLanguageSettingsUiState(
    val settingsState: LoadResult<Settings>
)

