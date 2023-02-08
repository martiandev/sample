package team.standalone.feature.other.ui.privacypolicy

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor(
) : BaseViewModel<PrivacyPolicyUiEvent>(), PrivacyPolicyUiEventListener

interface PrivacyPolicyUiEventListener

sealed class PrivacyPolicyUiEvent