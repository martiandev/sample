package team.standalone.feature.userinfo.ui.updatepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.disable
import team.standalone.core.common.extension.enable
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.DialogUtil
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.userinfo.R
import team.standalone.feature.userinfo.databinding.UserInfoFragmentUpdatePasswordBinding

@AndroidEntryPoint
class UpdatePasswordFragment : BaseFragment<UserInfoFragmentUpdatePasswordBinding>() {
    private val updatePasswordViewModel: UpdatePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentUpdatePasswordBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = updatePasswordViewModel
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(updatePasswordViewModel.enableUpdate) { enable ->
            if (enable) {
                dataBinding.buttonUpdatePassword.enable()
            } else {
                dataBinding.buttonUpdatePassword.disable()
            }
        }
        collectLatestLifecycleFlow(updatePasswordViewModel.updatePasswordResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                    showLoadingDialog()
                }
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    DialogUtil.showDialog(
                        context = requireContext(),
                        titleResId = R.string.user_info_dialog_update_password_success_title,
                        messageResId = R.string.user_info_dialog_update_password_success_message,
                        positiveLabelResId = R.string.user_info_dialog_update_password_success_btn_positive,
                        positiveBlock = {
                            findNavController().popBackStack()
                        },
                        cancellable = false
                    )
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    when (result.exception) {
                        is AuthException.NetworkException -> MessageUtil.toast(
                            context = requireContext(),
                            textResId = team.standalone.core.ui.R.string.uc_network_error
                        )
                        else -> DialogUtil.showDialog(
                            context = requireContext(),
                            titleResId = R.string.user_info_dialog_update_password_error_title,
                            messageResId = R.string.user_info_dialog_update_password_error_message,
                            positiveLabelResId = R.string.user_info_dialog_update_password_error_btn_positive,
                            positiveBlock = {
                                // do nothing
                            },
                            cancellable = true
                        )
                    }
                }
            }
        }
    }
}