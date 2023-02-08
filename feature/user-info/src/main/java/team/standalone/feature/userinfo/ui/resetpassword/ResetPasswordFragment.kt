package team.standalone.feature.userinfo.ui.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.userinfo.databinding.UserInfoFragmentResetPasswordBinding

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment<UserInfoFragmentResetPasswordBinding>() {
    private val resetPasswordViewModel: ResetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentResetPasswordBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = resetPasswordViewModel
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(resetPasswordViewModel.enableReset) { enabled ->
            dataBinding.buttonReset.isEnabled = enabled
        }
        collectLatestLifecycleFlow(resetPasswordViewModel.resetPasswordResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                    showLoadingDialog()
                }
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    ResetPasswordFragmentDirections.actionResetPasswordToResetPasswordFinish()
                        .navigate(findNavController())
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_reset_password_failed
                    )
                }
            }
        }
    }
}