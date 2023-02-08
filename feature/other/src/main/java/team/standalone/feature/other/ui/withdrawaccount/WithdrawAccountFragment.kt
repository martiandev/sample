package team.standalone.feature.other.ui.withdrawaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.clearAppData
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.core.ui.manager.DeepLinkManager
import team.standalone.feature.other.R
import team.standalone.feature.other.databinding.OtherFragmentWithdrawAccountBinding
import team.standalone.feature.other.ui.withdrawaccount.WithdrawAccountUiEvent.NavigateToDeleteAccountConfirmation
import team.standalone.feature.other.ui.withdrawaccount.WithdrawAccountUiEvent.NavigateToForgotPassword

@AndroidEntryPoint
class WithdrawAccountFragment : BaseFragment<OtherFragmentWithdrawAccountBinding>() {
    private val withdrawAccountViewModel: WithdrawAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = OtherFragmentWithdrawAccountBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = withdrawAccountViewModel
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(withdrawAccountViewModel.uiEvent) { uiEvent ->
            when(uiEvent) {
                NavigateToDeleteAccountConfirmation -> {
                    val builder = MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.other_dialog_withdraw_account_confirmation_title)
                        .setMessage(R.string.other_dialog_withdraw_account_confirmation_message)
                        .setNegativeButton(R.string.other_dialog_withdraw_account_confirmation_btn_negative) { _, _ ->
                            // do nothing
                        }
                        .setPositiveButton(R.string.other_dialog_withdraw_account_confirmation_btn_positive) { _, _ ->
                            withdrawAccountViewModel.deleteAccount()
                        }
                    builder.create().show()
                }
                NavigateToForgotPassword -> {
                    val uri = DeepLinkManager.resetPassword()
                    val request: NavDeepLinkRequest = NavDeepLinkRequest.Builder
                        .fromUri(uri)
                        .build()
                    val navOptions = NavOptions.Builder()
                        .setEnterAnim(team.standalone.core.ui.R.anim.enter_from_right)
                        .setExitAnim(team.standalone.core.ui.R.anim.exit_to_left)
                        .setPopEnterAnim(team.standalone.core.ui.R.anim.enter_from_left)
                        .setPopExitAnim(team.standalone.core.ui.R.anim.exit_to_right)
                        .build()
                    request.navigate(findNavController(), navOptions)
                }
            }
        }
        collectLatestLifecycleFlow(withdrawAccountViewModel.enableWithdraw) { enabled ->
            dataBinding.buttonWithdraw.isEnabled = enabled
        }
        collectLatestLifecycleFlow(withdrawAccountViewModel.deleteAccountResult) { result ->
            when (result) {
                is LoadResult.Loading -> showLoadingDialog()
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    val builder = MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.other_dialog_withdraw_account_success_title)
                        .setMessage(R.string.other_dialog_withdraw_account_success_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.other_dialog_withdraw_account_success_btn_positive) { _, _ ->
                            requireContext().clearAppData()
                            requireActivity().finish()
                        }
                    builder.create().show()
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_delete_account_failed
                    )
                }
            }
        }
    }
}