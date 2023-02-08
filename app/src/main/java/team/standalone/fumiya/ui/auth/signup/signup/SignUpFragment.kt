package team.standalone.fumiya.ui.auth.signup.signup

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
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentSignUpBinding
import team.standalone.fumiya.ui.auth.signup.signup.SignUpUiEvent.NavigateToVerification
import team.standalone.fumiya.ui.auth.signup.signup.SignUpUiEvent.SignUpValidation

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            withDataBinding(
                fragment = this,
                binding = FragmentSignUpBinding.inflate(inflater)
            )
        dataBinding.fragment = this
        dataBinding.viewModel = signUpViewModel
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
        var confirmPasswordVisibility = true

        dataBinding.tvShowPassword.setOnClickListener {
            passwordVisibility = passwordVisibility(passwordVisibility, dataBinding.tiPassword)
            cursorRemainedToLastChar(dataBinding.tiPassword)
        }

        dataBinding.tvShowConfirmPassword.setOnClickListener {
            confirmPasswordVisibility =
                passwordVisibility(confirmPasswordVisibility, dataBinding.tiConfirmPassword)
            cursorRemainedToLastChar(dataBinding.tiConfirmPassword)
        }
    }

    private fun passwordVisibility(
        visibility: Boolean,
        textInputEditText: TextInputEditText
    ): Boolean {
        return if (visibility) {
            textInputEditText
                .transformationMethod = HideReturnsTransformationMethod.getInstance()
            false
        } else {
            textInputEditText
                .transformationMethod = PasswordTransformationMethod.getInstance()
            true
        }
    }

    private fun cursorRemainedToLastChar(textInputEditText: TextInputEditText) {
        textInputEditText.text?.let { input ->
            textInputEditText.setSelection(
                input.length
            )
        }
    }

    override fun collectData() {
        collectLatestLifecycleFlow(signUpViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToVerification -> {
                    SignUpFragmentDirections
                        .actionSignUpFragmentToSignUpVerificationFragment()
                        .navigate(findNavController())
                }
                SignUpValidation -> {
                    showConfirmDialog()
                }
            }
        }

        collectLatestLifecycleFlow(signUpViewModel.signUpResult) { result ->
            when (result) {
                is LoadResult.Loading -> dataBinding.progressBar.visibility = View.VISIBLE
                is LoadResult.Success -> {
                    dataBinding.progressBar.visibility = View.GONE
                    signUpViewModel.sendUiEvent(NavigateToVerification)
                }
                is LoadResult.Error -> {
                    dataBinding.progressBar.visibility = View.GONE
                    when (val exception = result.exception) {
                        is AuthException -> {
                            when (exception) {
                                AuthException.NetworkException -> signUpShowAlertDialog(
                                    title = getString(R.string.fragment_sign_up_dialog_title_register_failed),
                                    message = getString(R.string.fragment_verification_authentication_network_error)
                                )
                                AuthException.EmailExistedException -> signUpShowAlertDialog(
                                    title = getString(R.string.fragment_sign_up_dialog_title_register_failed),
                                    message = getString(R.string.fragment_sign_up_dialog_message_register_failed)
                                )
                                else -> signUpShowAlertDialog(
                                    title = getString(R.string.fragment_sign_up_dialog_title_register_failed),
                                    message = getString(R.string.fragment_sign_up_unexpected_error_occur)
                                )
                            }
                        }
                        else -> signUpShowAlertDialog(
                            title = getString(R.string.fragment_sign_up_dialog_title_register_failed),
                            message = getString(R.string.fragment_sign_up_unexpected_error_occur)
                        )
                    }
                }
            }
        }
    }

    private fun showConfirmDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.fragment_sign_up_dialog_title_confirmation_registration)
            .setMessage(
                getString(
                    R.string.fragment_sign_up_dialog_message_register_confirmation,
                    dataBinding.tiEmailAddress.text.toString().trim()
                )
            )
            .setNegativeButton(R.string.fragment_sign_up_dialog_btn_cancel) { _, _ ->
                // do nothing
            }
            .setPositiveButton(R.string.fragment_sign_up_dialog_btn_ok) { _, _ ->
                signUpViewModel.registerUser()
            }
        builder.create().show()
    }

    private fun signUpShowAlertDialog(title: String, message: String) {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.fragment_sign_up_dialog_btn_ok) { _, _ ->
                // do nothing
            }
        builder.create().show()
    }
}