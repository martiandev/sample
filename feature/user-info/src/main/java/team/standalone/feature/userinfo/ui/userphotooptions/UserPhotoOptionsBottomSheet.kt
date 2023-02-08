package team.standalone.feature.userinfo.ui.userphotooptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.sendDestinationResult
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.ui.androidcomponent.BaseBottomSheetDialogFragment
import team.standalone.core.ui.util.NavKeys
import team.standalone.feature.userinfo.databinding.UserInfoBottomSheetUserPhotoOptionsBinding
import team.standalone.feature.userinfo.ui.userphotooptions.UserPhotoOptionsUiEvent.*

@AndroidEntryPoint
class UserPhotoOptionsBottomSheet :
    BaseBottomSheetDialogFragment<UserInfoBottomSheetUserPhotoOptionsBinding>() {
    private val userPhotoOptionsViewModel: UserPhotoOptionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoBottomSheetUserPhotoOptionsBinding.inflate(inflater)
        )
        dataBinding.bottomSheet = this
        dataBinding.viewModel = userPhotoOptionsViewModel
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(userPhotoOptionsViewModel.uiState) { uiState ->
            dataBinding.user = uiState.userState.data
        }
        collectLatestLifecycleFlow(userPhotoOptionsViewModel.uiEvent) { uiEvent ->
            when(uiEvent) {
                is SelectUserPhotoOption -> {
                    sendDestinationResult(NavKeys.USER_PHOTO_OPTION, uiEvent.userPhotoOption)
                    findNavController().popBackStack()
                }
            }
        }
    }
}