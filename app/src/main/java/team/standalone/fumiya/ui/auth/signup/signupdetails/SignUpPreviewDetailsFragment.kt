package team.standalone.fumiya.ui.auth.signup.signupdetails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.core.ui.manager.DeepLinkManager
import team.standalone.feature.billing.BillingSubscriptionActivity
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentSignUpPreviewDetailsBinding
import team.standalone.fumiya.ui.MainActivity
import team.standalone.fumiya.ui.auth.signup.signupdetails.SignUpPreviewDetailsUiEvent.*

@DelicateCoroutinesApi
@AndroidEntryPoint
class SignUpPreviewDetailsFragment : BaseFragment<FragmentSignUpPreviewDetailsBinding>() {
    private val signUpPreviewDetailsViewModel: SignUpPreviewDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentSignUpPreviewDetailsBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = signUpPreviewDetailsViewModel
        return dataBinding.root
    }

    private val subscriptionLauncherResult =
        registerForActivityResult(StartActivityForResult()) {
            startActivity(MainActivity.newIntent(requireActivity()))
        }

    private fun navigateToSubscriptionScreen() {
        subscriptionLauncherResult.launch(BillingSubscriptionActivity.newIntent(requireActivity()))
    }

    override fun collectData() {
        collectLatestLifecycleFlow(signUpPreviewDetailsViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToBackToInputDetails -> findNavController().popBackStack()
                NavigateToSubscriptionScreen -> navigateToSubscriptionScreen()
                is NavigateToPrivacyPolicy -> {
                    val title = getString(R.string.fragment_sign_up_title_privacy_policy)
                    val uri = DeepLinkManager.privacyPolicy(title)
                    displaysLocalHtmlFile(uri)
                }
                NavigateToAppTerms -> {
                    val uri = DeepLinkManager.termsOfService()
                    displaysLocalHtmlFile(uri)
                }
            }
        }
        collectLatestLifecycleFlow(signUpPreviewDetailsViewModel.registerUserResult) { result ->
            when (result) {
                is LoadResult.Loading -> dataBinding.progressBar.visibility = View.VISIBLE
                is LoadResult.Success -> {
                    dataBinding.progressBar.visibility = View.GONE
                    showSubscriptionDialog()
                }
                is LoadResult.Error -> {
                    dataBinding.progressBar.visibility = View.GONE
                    when (result.exception) {
                        is AuthException.NetworkException ->
                            MessageUtil.toast(
                                requireContext(),
                                getString(team.standalone.core.ui.R.string.uc_network_error)
                            )
                        else ->
                            MaterialAlertDialogBuilder(requireContext())
                                .setMessage(getString(R.string.fragment_sign_up_unexpected_error_occur))
                                .setPositiveButton(getString(team.standalone.core.ui.R.string.common_ok)) { _, _ -> }
                                .create()
                                .show()
                    }
                }
            }
        }
    }

    private fun showSubscriptionDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.fragment_sign_up_dialog_title_register_complete)
            .setMessage(R.string.fragment_sign_up_dialog_message_proceed_to_membership_page)
            .setCancelable(false)
            .setNegativeButton(R.string.fragment_sign_up_dialog_btn_pay_later) { _, _ ->
                startActivity(MainActivity.newIntent(requireActivity()))
                requireActivity().finish()
            }
            .setPositiveButton(R.string.fragment_sign_up_dialog_btn_ok) { _, _ ->
                signUpPreviewDetailsViewModel.sendUiEvent(NavigateToSubscriptionScreen)
            }
        builder.create().show()
    }

    private fun displaysLocalHtmlFile(uri: Uri) {
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