package team.standalone.feature.other.ui.termsofservice

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class TermsOfServiceViewModel @Inject constructor(
) : BaseViewModel<TermsOfServiceUiEvent>(), TermsOfServiceUiEventListener

interface TermsOfServiceUiEventListener

sealed class TermsOfServiceUiEvent