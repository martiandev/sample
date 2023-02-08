package team.standalone.fumiya.ui.park.tab

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.data.domain.usecase.GetLocaleUseCase
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.data.domain.usecase.firebase.GetTokenUseCase
import team.standalone.core.ui.viewmodel.BaseWebViewModel
import javax.inject.Inject

@HiltViewModel
class ParkViewModel @Inject constructor(
    getLocaleUseCase: GetLocaleUseCase,
    getTokenUseCase: GetTokenUseCase,
    getUserUseCase: GetUserUseCase
) : BaseWebViewModel<ParkUiEvent>(
    getLocaleUseCase,
    getTokenUseCase,
    getUserUseCase
), ParkUiEventListener

interface ParkUiEventListener

sealed class ParkUiEvent {
    object PhotoTab : ParkUiEvent()
    object VideoTab : ParkUiEvent()
}