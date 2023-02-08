package team.standalone.feature.userinfo.ui.updateemailfinish

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
import team.standalone.feature.userinfo.databinding.UserInfoFragmentUpdateEmailFinishBinding
import team.standalone.feature.userinfo.ui.updateemailfinish.UpdateEmailFinishUiEvent.NavigateToOther

@AndroidEntryPoint
class UpdateEmailFinishFragment : BaseFragment<UserInfoFragmentUpdateEmailFinishBinding>() {
    private val updateEmailFinishViewModel: UpdateEmailFinishViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentUpdateEmailFinishBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = updateEmailFinishViewModel
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(updateEmailFinishViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToOther -> UpdateEmailFinishFragmentDirections.actionExitUserInfoGraph()
                    .navigate(findNavController())
            }
        }
        collectLatestLifecycleFlow(updateEmailFinishViewModel.sendEmailVerificationResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                    showLoadingDialog()
                }
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_send_email_verification_success
                    )
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_send_email_verification_failed
                    )
                }
            }
        }
    }
}