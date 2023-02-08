package team.standalone.feature.other.ui.chatlanguagesettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.sendDestinationResult
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.ui.androidcomponent.BaseBottomSheetDialogFragment
import team.standalone.core.ui.util.NavKeys
import team.standalone.feature.other.databinding.OtherBottomSheetChatLanguageSettingsBinding
import team.standalone.feature.other.ui.chatlanguagesettings.ChatLanguageSettingsUiEvent.SelectLanguage

@AndroidEntryPoint
class ChatLanguageSettingsBottomSheet :
    BaseBottomSheetDialogFragment<OtherBottomSheetChatLanguageSettingsBinding>() {
    private val chatLanguageSettingsViewModel: ChatLanguageSettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = OtherBottomSheetChatLanguageSettingsBinding.inflate(inflater)
        )
        dataBinding.bottomSheet = this
        dataBinding.viewModel = chatLanguageSettingsViewModel
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(chatLanguageSettingsViewModel.uiState) { uiState ->
            dataBinding.settings = uiState.settingsState.data
        }
        collectLatestLifecycleFlow(chatLanguageSettingsViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                is SelectLanguage -> {
                    sendDestinationResult(NavKeys.CHAT_LANGUAGE, uiEvent.language)
                    findNavController().popBackStack()
                }
            }
        }
    }
}