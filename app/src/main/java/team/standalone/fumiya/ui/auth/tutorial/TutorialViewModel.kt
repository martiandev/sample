package team.standalone.fumiya.ui.auth.tutorial

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.CheckAuthenticatedUserUseCase
import team.standalone.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val checkAuthenticatedUserUseCase: CheckAuthenticatedUserUseCase
) : BaseViewModel<TutorialUiEvent>() {
    private val _authenticateResult = Channel<LoadResult<User>>()
    val authenticateResult = _authenticateResult.receiveAsFlow()

    init {
        runUseCase {
            _authenticateResult.trySend(LoadResult.Loading())
            val result = checkAuthenticatedUserUseCase()
            _authenticateResult.trySend(result.asLoadResult())
        }
    }
}

sealed class TutorialUiEvent