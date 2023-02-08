package team.standalone.feature.userinfo.ui.selectbirthdate

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SelectBirthdateViewModel @Inject constructor(
) : BaseViewModel<SelectBirthdateUiEvent>(), SelectBirthdateUiEventListener

interface SelectBirthdateUiEventListener

sealed class SelectBirthdateUiEvent