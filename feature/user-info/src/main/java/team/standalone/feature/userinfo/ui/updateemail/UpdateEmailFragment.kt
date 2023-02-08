package team.standalone.feature.userinfo.ui.updateemail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.userinfo.databinding.UserInfoFragmentUpdateEmailBinding
import team.standalone.feature.userinfo.ui.updateemail.UpdateEmailUiEvent.NavigateToUpdateEmailFinish

@AndroidEntryPoint
class UpdateEmailFragment : BaseFragment<UserInfoFragmentUpdateEmailBinding>() {
    private val updateEmailViewModel: UpdateEmailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentUpdateEmailBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = updateEmailViewModel
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(updateEmailViewModel.uiState) { uiState ->
            dataBinding.user = uiState.userState.data
        }
        collectLatestLifecycleFlow(updateEmailViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToUpdateEmailFinish -> {
                    UpdateEmailFragmentDirections.actionUpdateEmailToUpdateEmailFinish()
                        .navigate(findNavController())
                }
            }
        }
        collectLatestLifecycleFlow(updateEmailViewModel.updateEmailResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                    showLoadingDialog()
                }
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    updateEmailViewModel.sendUiEvent(NavigateToUpdateEmailFinish)
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    when (result.exception) {
                        is AuthException.EmailExistedException ->
                            updateEmailShowDialog(
                                message = getString(
                                    team.standalone.core.ui.R.string.uc_update_email_failed_email_already_registered
                                )
                            )
                        is AuthException.NetworkException -> {
                            MessageUtil.toast(
                                requireContext(),
                                getString(team.standalone.core.ui.R.string.uc_network_error)
                            )
                        }
                        else -> {
                            updateEmailShowDialog(
                                message = getString(
                                    team.standalone.core.ui.R.string.uc_unexpected_error
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateEmailShowDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(message)
            .setPositiveButton(getString(team.standalone.core.ui.R.string.common_ok)) { _, _ -> }
            .create()
            .show()
    }
}