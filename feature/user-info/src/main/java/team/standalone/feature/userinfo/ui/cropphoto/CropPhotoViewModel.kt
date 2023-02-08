package team.standalone.feature.userinfo.ui.cropphoto

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.UpdateUserPhotoUseCase
import team.standalone.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CropPhotoViewModel @Inject constructor(
    private val updateUserPhotoUseCase: UpdateUserPhotoUseCase,
) : BaseViewModel<CropPhotoUiEvent>(), CropPhotoUiEventListener {

    private val _updateUserPhotoResult = Channel<LoadResult<User>>()
    val updateUserPhotoResult = _updateUserPhotoResult.receiveAsFlow()

    override fun updatePhoto(uri: Uri) {
        runUseCase {
            _updateUserPhotoResult.send(LoadResult.Loading())
            val result = updateUserPhotoUseCase(uri)
            _updateUserPhotoResult.send(result.asLoadResult())
        }
    }
}

interface CropPhotoUiEventListener {
    fun updatePhoto(uri: Uri)
}

sealed class CropPhotoUiEvent