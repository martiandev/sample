package team.standalone.fumiya.ui.util.error

import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.fumiya.ui.util.error.NetworkErrorUiEvent.*
import javax.inject.Inject

@HiltViewModel
class ErrorViewModel @Inject constructor(
) : BaseViewModel<NetworkErrorUiEvent>(), NetworkErrorListener {

    override fun refresh() {
        sendUiEvent(ActionRefresh)
    }
}

interface NetworkErrorListener {
    fun refresh()
}

sealed class NetworkErrorUiEvent {
    object ActionRefresh : NetworkErrorUiEvent()
}