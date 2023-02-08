package team.standalone.feature.userinfo.ui.resignin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.core.ui.util.ReSignInInterest
import team.standalone.feature.userinfo.databinding.UserInfoFragmentReSignInBinding
import team.standalone.feature.userinfo.ui.resignin.ReSignInUiEvent.*

@AndroidEntryPoint
class ReSignInFragment : BaseFragment<UserInfoFragmentReSignInBinding>() {
    private val reSignInViewModel: ReSignInViewModel by viewModels()
    private val args: ReSignInFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentReSignInBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = reSignInViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.title = args.title
        dataBinding.description = args.description
    }

    override fun collectData() {
        collectLatestLifecycleFlow(reSignInViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToForgotPassword -> {
                    ReSignInFragmentDirections.actionReSignInToResetPassword()
                        .navigate(findNavController())
                }
                NavigateToUpdateEmail -> {
                    ReSignInFragmentDirections.actionReSignInToUpdateEmail()
                        .navigate(findNavController())
                }
                NavigateToUpdatePassword -> {
                    ReSignInFragmentDirections.actionReSignInToUpdatePassword()
                        .navigate(findNavController())
                }
            }
        }
        collectLatestLifecycleFlow(reSignInViewModel.signInResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                    showLoadingDialog()
                }
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    val event = when (args.interest) {
                        ReSignInInterest.UPDATE_EMAIL -> NavigateToUpdateEmail
                        ReSignInInterest.UPDATE_PASSWORD -> NavigateToUpdatePassword
                    }
                    reSignInViewModel.sendUiEvent(event)
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    val errorMessage = when (val exception = result.exception) {
                        is AuthException -> {
                            when (exception) {
                                AuthException.InvalidCredentialsException -> getString(team.standalone.core.ui.R.string.uc_invalid_credentials)
                                AuthException.NetworkException -> getString(team.standalone.core.ui.R.string.uc_network_error)
                                AuthException.EmailNotVerifiedException -> getString(team.standalone.core.ui.R.string.uc_re_sign_in_failed_unverified_email)
                                else -> getString(team.standalone.core.ui.R.string.uc_unexpected_error)
                            }
                        }
                        else -> getString(team.standalone.core.ui.R.string.uc_unexpected_error)
                    }

                    MaterialAlertDialogBuilder(requireContext())
                        .setMessage(errorMessage)
                        .setPositiveButton(getString(team.standalone.core.ui.R.string.common_ok)) { _, _ -> }
                        .create()
                        .show()
                }
            }
        }
    }
}