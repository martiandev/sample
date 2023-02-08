package team.standalone.fumiya.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.data.domain.usecase.GetLocaleUseCase
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.data.domain.usecase.firebase.GetTokenUseCase
import team.standalone.core.ui.viewmodel.BaseWebViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getLocaleUseCase: GetLocaleUseCase,
    val getTokenUseCase: GetTokenUseCase,
    getUserUseCase: GetUserUseCase,
) : BaseWebViewModel<MainUiEvent>(
    getLocaleUseCase,
    getTokenUseCase,
    getUserUseCase
), MainEventListener

interface MainEventListener

sealed class MainUiEvent




