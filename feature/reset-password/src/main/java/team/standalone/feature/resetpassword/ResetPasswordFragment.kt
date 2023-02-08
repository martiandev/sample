package team.standalone.feature.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.DialogUtil
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.resetpassword.ResetPasswordUiEvent.NavigateToVerification
import team.standalone.feature.resetpassword.databinding.FragmentResetPasswordBinding

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding>() {

    private val resetPasswordViewModel: ResetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentResetPasswordBinding.inflate(inflater)
        )
        dataBinding.viewModel = resetPasswordViewModel
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(
                false
            )
        }
        return dataBinding.root
    }

    override fun collectData() {
        collectLatestLifecycleFlow(resetPasswordViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToVerification ->
                    ResetPasswordFragmentDirections
                        .actionResetPasswordFragmentToResetPasswordVerificationFragment()
                        .navigate(findNavController())
            }
        }

        collectLatestLifecycleFlow(resetPasswordViewModel.resetPasswordResult) { result ->
            when (result) {
                is LoadResult.Loading -> dataBinding.progressBar.visibility = View.VISIBLE
                is LoadResult.Success -> {
                    dataBinding.progressBar.visibility = View.GONE
                    resetPasswordViewModel.sendUiEvent(NavigateToVerification)
                }
                is LoadResult.Error -> {
                    dataBinding.progressBar.visibility = View.GONE
                    when (result.exception) {
                        is AuthException.NetworkException -> MessageUtil.toast(
                            requireContext(), getString(
                                team.standalone.core.ui.R.string.uc_network_error
                            )
                        )
                        else -> DialogUtil.showDialog(
                            context = requireContext(),
                            titleResId = team.standalone.core.ui.R.string.uc_failed,
                            messageResId = R.string.fragment_reset_password_sending_failed,
                            positiveLabelResId = team.standalone.core.ui.R.string.common_close,
                            positiveBlock = { findNavController().popBackStack() },
                            cancellable = false
                        )
                    }
                }
            }
        }
    }
}