package team.standalone.fumiya.ui.park.photo

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.data.domain.usecase.GetLocaleUseCase
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.data.domain.usecase.firebase.GetTokenUseCase
import team.standalone.core.ui.viewmodel.BaseWebViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    getLocaleUseCase: GetLocaleUseCase,
    getTokenUseCase: GetTokenUseCase,
    getUserUseCase: GetUserUseCase
) : BaseWebViewModel<PhotoUiEvent>(
    getLocaleUseCase,
    getTokenUseCase, getUserUseCase
), PhotoUiEventListener

interface PhotoUiEventListener

sealed class PhotoUiEvent