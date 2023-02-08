package team.standalone.fumiya.ui.live

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.data.domain.usecase.GetLocaleUseCase
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.data.domain.usecase.firebase.GetTokenUseCase
import team.standalone.core.ui.viewmodel.BaseWebViewModel
import javax.inject.Inject

@HiltViewModel
class LiveViewModel @Inject constructor(
    getLocaleUseCase: GetLocaleUseCase,
    getTokenUseCase: GetTokenUseCase,
    getUserUseCase: GetUserUseCase
) : BaseWebViewModel<LiveUiEvent>(
    getLocaleUseCase,
    getTokenUseCase,
    getUserUseCase
), LiveUiEventListener

interface LiveUiEventListener

sealed class LiveUiEvent