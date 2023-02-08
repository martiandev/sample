package team.standalone.feature.other.ui.other

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.text.buildSpannedString
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.*
import team.standalone.core.common.qualifier.AppVersionCode
import team.standalone.core.common.qualifier.AppVersionName
import team.standalone.core.common.qualifier.BillingItemSubscribe
import team.standalone.core.common.util.*
import team.standalone.core.data.domain.model.Settings
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.core.ui.util.NavKeys
import team.standalone.feature.other.R
import team.standalone.feature.other.databinding.OtherFragmentOtherBinding
import team.standalone.feature.other.ui.other.OtherUiEvent.*
import javax.inject.Inject

@AndroidEntryPoint
class OtherFragment : BaseFragment<OtherFragmentOtherBinding>() {
    private val otherViewModel: OtherViewModel by viewModels()

    @Inject
    @AppVersionName
    lateinit var appVersionName: String

    @Inject
    @AppVersionCode
    lateinit var appVersionCode: String

    @Inject
    @BillingItemSubscribe
    lateinit var billingItemSubscribe: String

    private val manageSubscriptionLauncher =
        registerForActivityResult(StartActivityForResult()) {
            OtherFragmentDirections.actionOtherToWithdrawAccount()
                .navigate(findNavController())
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = OtherFragmentOtherBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = otherViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.appVersionName = appVersionName
        dataBinding.appVersionCode = appVersionCode
    }

    override fun collectData() {
        collectLatestLifecycleFlow(otherViewModel.uiState) { uiState ->
            dataBinding.user = uiState.userState.data
        }
        collectLatestLifecycleFlow(otherViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                NavigateToChatLanguageSettings -> OtherFragmentDirections.actionOtherToChatLanguageSettings()
                    .navigate(findNavController())
                NavigateToWithdrawAccount -> OtherFragmentDirections.actionOtherToWithdrawAccount()
                    .navigate(findNavController())
                NavigateToDeleteAccountWarning -> showDeleteAccountWarning()
                NavigateToLicense -> {
                    Intent(requireContext(), OssLicensesMenuActivity::class.java).apply {
                        putExtra("title", getString(R.string.other_activity_license_title))
                    }.let(::startActivity)
                }
                NavigateToSignOutConfirmation -> {
                    DialogUtil.showDialog(
                        context = requireContext(),
                        titleResId = R.string.other_dialog_sign_out_confirmation_title,
                        messageResId = R.string.other_dialog_sign_out_confirmation_message,
                        negativeLabelResId = R.string.other_dialog_sign_out_confirmation_btn_negative,
                        negativeBlock = {
                            // do nothing
                        },
                        positiveLabelResId = R.string.other_dialog_sign_out_confirmation_btn_positive,
                        positiveBlock = {
                            otherViewModel.signOut()
                        }
                    )
                }
                NavigateToUserInformation -> OtherFragmentDirections.actionOtherToNavigationUserInfo()
                    .navigate(findNavController())
                is OpenBrowser -> IntentUtil.getBrowserIntent(uiEvent.url)?.let(::startActivity)
                OpenManageSubscription -> IntentUtil.getGooglePlayStoreSubscriptionIntent(
                    packageName = requireContext().packageName,
                    sku = billingItemSubscribe
                )?.also(manageSubscriptionLauncher::launch)
                NavigateToTermsOfService -> OtherFragmentDirections.actionOtherFragmentToTermsOfService()
                    .navigate(findNavController())
                NavigateToPrivacyPolicy -> OtherFragmentDirections.actionOtherFragmentToPrivacyPolicy(
                    title = getString(R.string.other_fragment_privacy_policy_title)
                ).navigate(findNavController())
            }
        }
        collectLatestLifecycleFlow(otherViewModel.updateLanguageResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                }
                is LoadResult.Success -> {
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_update_language_success
                    )
                }
                is LoadResult.Error -> {
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_update_language_failed
                    )
                }
            }
        }
        collectLatestLifecycleFlow(otherViewModel.signOutResult) { result ->
            when (result) {
                is LoadResult.Loading -> {
                }
                is LoadResult.Success -> {
                    requireContext().clearAppData()
                    requireActivity().finish()
                }
                is LoadResult.Error -> {
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_sign_out_failed
                    )
                }
            }
        }
    }

    override fun observeDestinationResult() {
        observeDialogResult<Settings.Language>(
            key = NavKeys.CHAT_LANGUAGE,
            destinationId = R.id.otherFragment
        ) { language ->
            language?.let {
                otherViewModel.updateLanguage(it)
            }
        }
    }

    private fun showDeleteAccountWarning() {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setNegativeButton(getString(R.string.other_dialog_delete_account_warning_btn_negative)) { _, _ -> }
            .setPositiveButton(getString(R.string.other_dialog_delete_account_warning_btn_positive)) { _, _ ->
                if (dataBinding.user?.isMember().orFalse()) {
                    otherViewModel.openManageSubscription()
                } else {
                    otherViewModel.openWithdrawAccount()
                }
            }
            .create()

        dialog.setTitle(getString(R.string.other_dialog_delete_account_warning_title))
        dialog.setMessage(buildSpannedString {
            append(getString(R.string.other_dialog_delete_account_warning_message))
            appendLine()
            append(getString(R.string.other_dialog_delete_account_warning_message_item_1))
            appendLine()
            append(getString(R.string.other_dialog_delete_account_warning_message_item_2))
            appendLine()
            append(SpannableString(getString(R.string.other_dialog_delete_account_warning_message_item_3)).apply {
                setSpan(object : ClickableSpan() {
                    override fun onClick(p0: View) {
                        IntentUtil.getGooglePlayStoreSubscriptionIntent(
                            packageName = requireContext().packageName,
                            sku = billingItemSubscribe
                        )?.also(::startActivity)
                        dialog.dismiss()
                    }

                    override fun updateDrawState(textPaint: TextPaint) {
                        textPaint.apply { color = Color.BLUE }
                    }
                }, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            })
        })
        dialog.show()

        (dialog.findViewById(android.R.id.message) as? TextView)?.apply {
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}