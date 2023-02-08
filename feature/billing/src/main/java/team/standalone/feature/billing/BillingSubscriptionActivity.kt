package team.standalone.feature.billing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.model.PurchaseResult
import team.standalone.core.ui.androidcomponent.BaseActivity
import team.standalone.feature.billing.databinding.ActivityBillingSubscriptionBinding

@AndroidEntryPoint
@DelicateCoroutinesApi
class BillingSubscriptionActivity : BaseActivity<ActivityBillingSubscriptionBinding>() {

    private val billingSubscriptionViewModel: BillingSubscriptionViewModel by viewModels()

    private var canGoBack: Boolean = true

    companion object {
        fun newIntent(fragmentActivity: FragmentActivity) =
            Intent(fragmentActivity, BillingSubscriptionActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityBillingSubscriptionBinding.inflate(layoutInflater)
        dataBinding.lifecycleOwner = this@BillingSubscriptionActivity
        dataBinding.activity = this
        dataBinding.viewModel = billingSubscriptionViewModel
        setContentView(dataBinding.root)

        lifecycle.addObserver(billingSubscriptionViewModel)

        this.onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (canGoBack) {
                        finish()
                    }
                }
            })
    }

    override fun collectData() {
        collectLatestLifecycleFlow(billingSubscriptionViewModel.loading) { result ->
            when (result) {
                true -> {
                    canGoBack = false
                    dataBinding.progressBar.visibility = View.VISIBLE
                }
                false -> {
                    canGoBack = true
                    dataBinding.progressBar.visibility = View.GONE
                }
            }
        }

        collectLatestLifecycleFlow(billingSubscriptionViewModel.subscription) { result ->
            when (result) {
                PurchaseResult.Success -> showSubscriptionDialog(
                    getString(R.string.billing_subscription_dialog_title_success),
                    getString(R.string.billing_subscription_dialog_message_success)
                )
                PurchaseResult.Cancelled -> showSubscriptionDialog(
                    getString(R.string.billing_subscription_dialog_title_cancelled),
                    getString(R.string.billing_subscription_dialog_message_cancelled)
                )
                PurchaseResult.AlreadyOwned -> showSubscriptionDialog(
                    getString(R.string.billing_subscription_dialog_title_already_owned),
                    getString(R.string.billing_subscription_dialog_message_already_owned)
                )
                PurchaseResult.Pending -> showSubscriptionDialog(
                    getString(R.string.billing_subscription_dialog_title_pending),
                    getString(R.string.billing_subscription_dialog_message_pending)
                )
                else -> showSubscriptionDialog(
                    getString(R.string.billing_subscription_dialog_title_failed),
                    getString(R.string.billing_subscription_dialog_message_failed)
                )
            }
        }
    }

    private fun showSubscriptionDialog(title: String, message: String) {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.billing_subscription_dialog_btn_close) { _, _ -> finish() }
        builder.create().show()
    }

    fun goToMain() = finish()

    fun subscribeToApp() = billingSubscriptionViewModel.onPurchase(this)

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(billingSubscriptionViewModel)
    }
}