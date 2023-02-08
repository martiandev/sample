package team.standalone.feature.userinfo.ui.userphotooptions

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import team.standalone.core.common.util.LoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.ui.util.UserPhotoOption
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.feature.userinfo.ui.userphotooptions.UserPhotoOptionsUiEvent.*
import javax.inject.Inject

@HiltViewModel
class UserPhotoOptionsViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
) : BaseViewModel<UserPhotoOptionsUiEvent>(), UserPhotoOptionsUiEventListener {
    val uiState = getUserUseCase(true)
        .map { result ->
            UserPhotoOptionsUiState(result)
        }.stateIn(
            scope = viewModelScope,
            started = whileSubscribed,
            initialValue = UserPhotoOptionsUiState(LoadResult.Loading())
        )

    override fun selectUserPhotoOption(userPhotoOption: UserPhotoOption) =
        sendUiEvent(SelectUserPhotoOption(userPhotoOption))
}

interface UserPhotoOptionsUiEventListener {
    fun selectUserPhotoOption(userPhotoOption: UserPhotoOption)
}

sealed class UserPhotoOptionsUiEvent {
    data class SelectUserPhotoOption(
        val userPhotoOption: UserPhotoOption
    ) : UserPhotoOptionsUiEvent()
}

data class UserPhotoOptionsUiState(
    val userState: LoadResult<User>
)

