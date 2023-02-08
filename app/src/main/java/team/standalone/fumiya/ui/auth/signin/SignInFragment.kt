package team.standalone.fumiya.ui.auth.signin

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.exception.UserException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentSignInBinding
import team.standalone.fumiya.ui.MainActivity
import team.standalone.fumiya.ui.auth.signin.LoginUiEvent.*

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            withDataBinding(
                fragment = this,
                binding = FragmentSignInBinding.inflate(inflater)
            )
        dataBinding.viewModel = signInViewModel
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(
                false
            )
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var passwordVisibility = true

        dataBinding.tvShowPassword.setOnClickListener {
            if (passwordVisibility) {
                passwordVisibility = false
                dataBinding.tiPassword
                    .transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                passwordVisibility = true
                dataBinding.tiPassword
                    .transformationMethod = PasswordTransformationMethod.getInstance()
            }
            dataBinding.tiPassword.text?.let { input ->
                dataBinding.tiPassword.setSelection(
                    input.length
                )
            }
        }
    }

    override fun collectData() {
        collectLatestLifecycleFlow(signInViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToDashboard -> {
                    startActivity(MainActivity.newIntent(requireActivity()))
                    requireActivity().finish()
                }
                NavigateToInputDetails -> {
                    SignInFragmentDirections
                        .actionSignInFragmentToSignUpInputDetailsFragment()
                        .navigate(findNavController())
                }
                NavigateToRegister -> {
                    SignInFragmentDirections
                        .actionSignInFragmentToSignUpFragment()
                        .navigate(findNavController())
                }
                NavigateToForgotPassword -> {
                    SignInFragmentDirections
                        .actionSignInFragmentToNavigationResetPassword()
                        .navigate(findNavController())
                }
            }
        }

        collectLatestLifecycleFlow(signInViewModel.signInResult) { result ->
            when (result) {
                is LoadResult.Loading -> dataBinding.progressBar.visibility = View.VISIBLE
                is LoadResult.Success -> {
                    dataBinding.progressBar.visibility = View.GONE
                    signInViewModel.sendUiEvent(NavigateToDashboard)
                }
                is LoadResult.Error -> {
                    dataBinding.progressBar.visibility = View.GONE
                    Lumberjack.error("exception: ${result.exception}")
                    when (val exception = result.exception) {
                        is AuthException -> {
                            when (exception) {
                                AuthException.EmailNotVerifiedException -> showVerificationDialog()
                                AuthException.InvalidCredentialsException -> {
                                    signInViewModel.password.hasError.set(true)
                                    signInViewModel.password.errMsg.set(getString(R.string.fragment_sign_in_validation_invalid_credentials))
                                }
                                AuthException.InvalidUserException -> {
                                    signInViewModel.emailAddress.hasError.set(true)
                                    signInViewModel.emailAddress.errMsg.set(getString(R.string.fragment_sign_in_validation_invalid_user))
                                }
                                AuthException.NetworkException -> signInShowAlertDialog(
                                    title = getString(R.string.fragment_sign_in_dialog_title_login_error),
                                    message = getString(R.string.fragment_verification_authentication_network_error)
                                )
                                else -> signInShowAlertDialog(
                                    title = getString(R.string.fragment_sign_in_dialog_title_login_error),
                                    message = getString(R.string.fragment_sign_in_unexpected_error_occur)
                                )
                            }
                        }
                        is UserException -> {
                            when (exception) {
                                UserException.UserNotFoundException ->
                                    signInViewModel.sendUiEvent(NavigateToInputDetails)
                                else -> signInShowAlertDialog(
                                    title = getString(R.string.fragment_sign_in_dialog_title_login_error),
                                    message = getString(R.string.fragment_sign_in_unexpected_error_occur)
                                )
                            }
                        }
                    }
                }
            }
        }

        collectLatestLifecycleFlow(signInViewModel.verificationResult) {
            when (it) {
                is LoadResult.Loading -> dataBinding.progressBar.visibility = View.VISIBLE
                is LoadResult.Success -> {
                    dataBinding.progressBar.visibility = View.GONE
                    signInShowAlertDialog(
                        title = getString(R.string.fragment_sign_in_dialog_title_resend_result),
                        message = getString(R.string.fragment_verification_authentication_sent)
                    )
                }
                is LoadResult.Error -> {
                    dataBinding.progressBar.visibility = View.GONE
                    signInShowAlertDialog(
                        title = getString(R.string.fragment_sign_in_dialog_title_resend_result),
                        message = getString(R.string.fragment_verification_authentication_failed)
                    )
                }
            }
        }
    }

    private fun showVerificationDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.fragment_sign_in_dialog_title_login_error)
            .setMessage(R.string.fragment_sign_in_dialog_message_email_unverified)
            .setNegativeButton(R.string.fragment_sign_in_dialog_btn_close) { _, _ ->
                // do nothing
            }
            .setPositiveButton(R.string.fragment_sign_in_dialog_btn_resend) { _, _ ->
                signInViewModel.resendVerification()
            }
        builder.create().show()
    }


    private fun signInShowAlertDialog(title: String, message: String) {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.fragment_sign_in_dialog_btn_close) { _, _ ->
                // do nothing
            }
        builder.create().show()
    }

    override fun onResume() {
        super.onResume()
        dataBinding.tiPassword.setText("")
    }
}