package team.standalone.feature.userinfo.ui.userinfo

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.DeleteUserPhotoUseCase
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.feature.userinfo.ui.userinfo.UserInfoUiEvent.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserInfoViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val deleteUserPhotoUseCase: DeleteUserPhotoUseCase
) : BaseViewModel<UserInfoUiEvent>(), UserInfoUiEventListener {

    val uiState = getUserUseCase(true)
        .mapLatest { result ->
            UserInfoUiState(result)
        }.stateIn(
            scope = viewModelScope,
            started = whileSubscribed,
            initialValue = UserInfoUiState(LoadResult.Loading())
        )

    private val _deleteUserPhotoResult = Channel<LoadResult<User>>()
    val deleteUserPhotoResult = _deleteUserPhotoResult.receiveAsFlow()

    override fun openUserPhotoOptions() = sendUiEvent(NavigateToUserPhotoOptions)

    override fun openCropPhoto(filePath: String) = sendUiEvent(NavigateToCropPhoto(filePath))

    override fun openUpdateUser() = sendUiEvent(NavigateToUpdateUser)

    override fun openUpdateEmail() = sendUiEvent(NavigateToUpdateEmail)

    override fun openUpdatePassword() = sendUiEvent(NavigateToUpdatePassword)

    override fun deleteUserPhoto() {
        runUseCase {
            _deleteUserPhotoResult.send(LoadResult.Loading())
            val result = deleteUserPhotoUseCase()
            _deleteUserPhotoResult.send(result.asLoadResult())
        }
    }
}

interface UserInfoUiEventListener {
    fun openUserPhotoOptions()
    fun openCropPhoto(filePath: String)
    fun openUpdateUser()
    fun openUpdateEmail()
    fun openUpdatePassword()
    fun deleteUserPhoto()
}

sealed class UserInfoUiEvent {
    object NavigateToUserPhotoOptions : UserInfoUiEvent()
    object NavigateToUpdateUser : UserInfoUiEvent()
    object NavigateToUpdateEmail : UserInfoUiEvent()
    object NavigateToUpdatePassword : UserInfoUiEvent()
    data class NavigateToCropPhoto(val filePath: String) : UserInfoUiEvent()
}

data class UserInfoUiState(
    val userState: LoadResult<User>
)