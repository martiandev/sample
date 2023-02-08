package team.standalone.feature.userinfo.ui.userinfo

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.observeDialogResult
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.qualifier.AppFileProvider
import team.standalone.core.common.qualifier.AppVersionCode
import team.standalone.core.common.qualifier.AppVersionName
import team.standalone.core.common.util.*
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.core.ui.util.NavKeys
import team.standalone.core.ui.util.ReSignInInterest
import team.standalone.core.ui.util.UserPhotoOption
import team.standalone.feature.userinfo.R
import team.standalone.feature.userinfo.databinding.UserInfoFragmentUserInfoBinding
import team.standalone.feature.userinfo.ui.userinfo.UserInfoUiEvent.*
import javax.inject.Inject

@AndroidEntryPoint
class UserInfoFragment : BaseFragment<UserInfoFragmentUserInfoBinding>() {
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    @Inject
    @AppVersionName
    lateinit var appVersionName: String

    @Inject
    @AppVersionCode
    lateinit var appVersionCode: String

    @Inject
    @AppFileProvider
    lateinit var fileProvider: String

    private var photoUri: Uri? = null

    private val cameraPermission = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            photoUri = StorageUtil.createSharedCacheFile(
                context = requireContext(),
                mimeType = StorageUtil.MimeType.PNG
            )?.let { file ->
                FileProvider.getUriForFile(
                    requireContext(),
                    fileProvider,
                    file
                )
            }
            photoUri?.let { takePicture.launch(it) }
        }
    }

    private val gallery = registerForActivityResult(GetContent()) { uri ->
        uri?.let {
            StorageUtil.copy(requireContext(), it)?.let { file ->
                // todo check why event is not published here
                // userInfoViewModel.openCropPhoto(file.absolutePath)
                UserInfoFragmentDirections.actionUserInfoToCropPhoto(file.absolutePath)
                    .navigate(findNavController())
            }
        }
    }

    private val takePicture = registerForActivityResult(TakePicture()) { success ->
        if (success) {
            photoUri?.let { uri ->
                StorageUtil.copy(requireContext(), uri)?.let { file ->
                    // todo check why event is not published here
                    // userInfoViewModel.openCropPhoto(file.absolutePath)
                    UserInfoFragmentDirections.actionUserInfoToCropPhoto(file.absolutePath)
                        .navigate(findNavController())
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentUserInfoBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = userInfoViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun collectData() {
        collectLatestLifecycleFlow(userInfoViewModel.uiState) { uiState ->
            dataBinding.user = uiState.userState.data
        }
        collectLatestLifecycleFlow(userInfoViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToUserPhotoOptions -> {
                    UserInfoFragmentDirections.actionUserInfoToUserPhotoOptions()
                        .navigate(findNavController())
                }
                NavigateToUpdateUser -> {
                    UserInfoFragmentDirections.actionUserInfoToUpdateUser()
                        .navigate(findNavController())
                }
                NavigateToUpdateEmail -> {
                    UserInfoFragmentDirections.actionUserInfoToReSignIn(
                        title = getString(R.string.user_info_fragment_re_sign_in_update_email_title),
                        description = getString(R.string.user_info_fragment_re_sign_in_update_password_description),
                        interest = ReSignInInterest.UPDATE_EMAIL
                    ).navigate(findNavController())
                }
                NavigateToUpdatePassword -> {
                    UserInfoFragmentDirections.actionUserInfoToReSignIn(
                        title = getString(R.string.user_info_fragment_re_sign_in_update_password_title),
                        description = getString(R.string.user_info_fragment_re_sign_in_update_password_description),
                        interest = ReSignInInterest.UPDATE_PASSWORD
                    ).navigate(findNavController())
                }
                is NavigateToCropPhoto -> {
                    UserInfoFragmentDirections.actionUserInfoToCropPhoto(uiEvent.filePath)
                        .navigate(findNavController())
                }
            }
        }
        collectLatestLifecycleFlow(userInfoViewModel.deleteUserPhotoResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                    showLoadingDialog()
                }
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_delete_user_photo_success
                    )
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    val errorMessage = when (result.exception) {
                        is AuthException.NetworkException -> getString(team.standalone.core.ui.R.string.uc_network_error)
                        else -> getString(team.standalone.core.ui.R.string.uc_delete_user_photo_failed)
                    }
                    MessageUtil.toast(
                        context = requireContext(),
                        text = errorMessage
                    )
                }
            }
        }
    }

    override fun observeDestinationResult() {
        observeDialogResult<UserPhotoOption>(
            key = NavKeys.USER_PHOTO_OPTION,
            destinationId = R.id.userInfoFragment
        ) { userPhotoOption ->
            userPhotoOption?.let(::updatePhoto)
        }
    }

    private fun updatePhoto(userPhotoOption: UserPhotoOption) {
        when (userPhotoOption) {
            UserPhotoOption.CAMERA -> {
                cameraPermission.launch(Manifest.permission.CAMERA)
            }
            UserPhotoOption.GALLERY -> {
                gallery.launch("image/*")
            }
            UserPhotoOption.DELETE -> {
                userInfoViewModel.deleteUserPhoto()
            }
        }
    }
}